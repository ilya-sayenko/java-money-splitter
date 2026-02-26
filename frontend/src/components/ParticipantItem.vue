<script setup lang="ts">
import type {Participant} from "@/models/Participant.ts";
import {computed, nextTick, reactive, ref, useTemplateRef} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {useRoute} from "vue-router";
import {ParticipantUpdateRequest} from "@/http/data/models/ParticipantUpdateRequest.ts";
import {useI18n} from "vue-i18n";
import {helpers, required} from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";
import {storeToRefs} from "pinia";
import {errorMessage} from "@/utils/errorMessage.ts";
import type {Spending} from "@/models/Spending.ts";
import type {Proportion} from "@/models/Proportion.ts";
import {SplitType} from "@/models/SplitType.ts";

const props = defineProps<{
  participant: Participant;
}>();

const { t } = useI18n();
const isEditParticipant = ref(false);
const participantNewNameInput = useTemplateRef("participantNewNameInput");

const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();
const { participants, spendings } = storeToRefs(partyStore);
const isCouldNotBeDeleted = ref(false);

const formState = reactive({
  participantNewName: ''
});

const rules = computed(() => ({
  participantNewName: {
    required: helpers.withMessage(t('errors.participantNameRequired'), required),
    unique: helpers.withMessage(
      t('errors.participantNameUnique'),
      (value: string) => !participants.value || !participants.value.map(p => p.name).includes(value)
    )
  }
}))

const v$ = useVuelidate(rules, formState, { $stopPropagation: true });

function isDeletable(participantId: string) {
  for (let spending: Spending of spendings.value) {
    if (spending.payer.id === participantId) {
      return false;
    }

    if (spending.splitType !== SplitType.EQUAL) {
      for (let proportion: Proportion of spending.proportions) {
        if (proportion.participant.id === participantId) {
          return false;
        }
      }
    }
  }

  return true;
}

async function deleteParticipantById(participantId: string) {
  if (!isDeletable(participantId)) {
    isCouldNotBeDeleted.value = true;
    setTimeout(() => {
      isCouldNotBeDeleted.value = false;
    }, 3000);

    return;
  }

  await partyStore.deleteParticipantById(participantId);
  await Promise.all([
    partyStore.loadSpendingsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value),
    partyStore.loadParticipantsByPartyId(partyId.value)
  ]);
}

function editParticipant() {
  isEditParticipant.value = true;
  formState.participantNewName = props.participant.name;
  nextTick(() => {
    if (participantNewNameInput.value) {
      participantNewNameInput.value.focus();
    }
  });
}

async function saveParticipant() {
  if (props.participant.name === formState.participantNewName) {
    isEditParticipant.value = false;
    return;
  }

  const isValidForm = await v$.value.$validate();

  if (!isValidForm) {
    return;
  }

  const request = new ParticipantUpdateRequest();
  request.id = props.participant.id;
  request.name = formState.participantNewName;
  await partyStore.updateParticipant(request);
  isEditParticipant.value = false;
  await Promise.all([
    partyStore.loadParticipantsByPartyId(partyId.value),
    partyStore.loadSpendingsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value)
  ]);

  v$.value.$reset();
}
</script>

<template>
  <li class="participant-item">
    <div class="participant-name">
      <span class="participant-item-name" v-show="!isEditParticipant">{{ participant.name }}</span>
      <small class="error error-left" v-if="isCouldNotBeDeleted">{{ t('errors.participantDelete') }}</small>
    </div>

    <div v-show="isEditParticipant" class="edit-participant">
      <input
        class="participant-item-new-name"
        type="text"
        ref="participantNewNameInput"
        @blur="saveParticipant()"
        @keyup.enter="saveParticipant()"
        @input="v$.participantNewName.$reset()"
        v-model="formState.participantNewName"
      />
      <small class="error" v-if="v$.participantNewName.$error">{{ errorMessage(v$.participantNewName.$errors) }}</small>
    </div>

    <div class="btn-edit-delete">
      <button :title="t('titles.editParticipant')" @click="editParticipant">✏️</button>
      <button :title="t('titles.deleteParticipant')" class="tooltip" @click="deleteParticipantById(participant.id)">❌</button>
    </div>
  </li>
</template>

<style lang="scss" scoped>
.edit-participant {
  display: flex;
  flex-direction: column;
}

.participant-name {
  display: flex;
  flex-direction: column;
}

.error-left {
  text-align: left;
}
</style>