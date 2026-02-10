import { createI18n } from "vue-i18n"

const locale = localStorage.getItem('locale') || 'ru'

export const i18n = createI18n({
  legacy: false,
  globalInjection: true,
  locale: locale,
  messages: {
    en: {
      headers: {
        authorization: 'Authorization',
        myParties: 'My Parties',
        participants: 'Participants',
        spendings: 'Spendings',
        transactions: 'Transactions',
        statistics: 'Participant spending statistics',
        createParty: 'Create party'
      },
      buttons: {
        signIn: 'Sign In',
        signUp: 'Sign Up',
        createParty: 'Create party',
        update: 'Update',
        signOut: 'Sign Out',
        addParticipant: 'Add participant',
        addSpending: 'Add spending',
      },
      labels: {
        email: 'E-mail',
        password: 'Password',
        total: 'Total',
        participants: 'participants',
        participantName: "Participant's name",
        spendings: 'spendings',
        name: 'Name',
        spendingName: 'Name of spending',
        description: 'Description',
        payer: 'Payer',
        amount: 'Amount',
      },
      userPopup: {
        profile: 'Profile',
        events: 'My parties',
        statistics: 'Statistics',
        signOut: 'Sign Out'
      },
      userPage: {
        events: 'My parties',
        eventsDescription: 'List of your parties and participants',
        statistics: 'Statistics',
        statisticsDescription: 'Participant spending statistics'
      },
      statisticsPage: {
        sum: 'Sum of all spendings'
      },
      transactionsCard: {
        description: 'The calculation of debts will be shown here.',
        debts: 'To pay off debts, you need to make transactions'
      },
      participantsCard: {
        list: 'List of participants',
      },
      spendingsCard: {
        list: 'List of spendings'
      }
    },

    ru: {
      headers: {
        authorization: 'Авторизация',
        myParties: 'My Events',
        participants: 'Participants',
        spendings: 'Spendings',
        transactions: 'Кто кому должен?',
        statistics: 'Статистика участников по расходам',
        createParty: 'Создать событие',
      },
      buttons: {
        signIn: 'Войти',
        signUp: 'Зарегистрироваться',
        createParty: 'Создать событие',
        update: 'Update',
        signOut: 'Sign Out',
        addParticipant: 'Добавить участника',
        addSpending: 'Add spending',

      },
      labels: {
        email: 'E-mail',
        password: 'Пароль',
        total: 'Всего',
        participants: 'участник(ов)',
        participantName: "Имя участника",
        spendings: 'расход(ов)',
        payer: 'Кто платил?',
        spendingName: 'За что?',
        description: 'Описание',
        name: 'Название',
        amount: 'Сумма',
      },
      userPopup: {
        profile: 'Профиль',
        events: 'Мои события',
        statistics: 'Статистика',
        signOut: 'Выйти'
      },
      userPage: {
        events: 'Мои события',
        eventsDescription: 'Список ваших событий и участников',
        statistics: 'Статистика',
        statisticsDescription: 'Статистика участников по расходам'
      },
      statisticsPage: {
        sum: 'Сумма всех расходов'
      },
      transactionsCard: {
        description: 'Здесь будет показан расчёт задолженностей.',
        debts: 'Для погашения долгов нужно сделать переводы',
        transaction: "{} transfers {} to {}"
      },
      participantsCard: {
        list: 'Список участников'
      },
      spendingsCard: {
        list: 'Список расходов'
      }
    }
  }
})