import { createI18n } from "vue-i18n"

export const locale = localStorage.getItem('locale') || 'ru';

export const currency = locale == 'ru' ? 'RUB' : 'USD';

export const i18n = createI18n({
  legacy: false,
  globalInjection: true,
  locale: locale,
  numberFormats: {
    en: {
      currency: {
        style: 'currency',
        currency: 'USD'
      }
    },
    ru: {
      currency: {
        style: 'currency',
        currency: 'RUB'
      }
    }
  },
  messages: {
    en: {
      headers: {
        authorization: 'Authorization',
        myParties: 'My Parties',
        participants: 'Participants',
        spendings: 'Spendings',
        transactions: 'Transactions',
        statistics: 'Participant spending statistics',
        createParty: 'Create party',
        updateParty: 'Update party',
        deleteParty: 'Delete party',
        createSpending: 'Create spending'
      },
      buttons: {
        signIn: 'Sign In',
        signUp: 'Sign Up',
        createParty: 'Create party',
        updateParty: 'Update party',
        update: 'Update',
        signOut: 'Sign Out',
        addParticipant: 'Add participant',
        addSpending: 'Add spending',
        yes: 'Yes',
        no: 'No'
      },
      labels: {
        email: 'E-mail',
        password: 'Password',
        total: 'Total',
        participants: 'Participants',
        participantsAlt: 'participants',
        participantName: "Participant name",
        spendings: 'spendings',
        spendingsAlt: 'spendings',
        name: 'Name',
        spendingName: 'Name of spending',
        description: 'Description',
        payer: 'Payer',
        amount: 'Amount',
        all: 'All',
        selected: 'Selected',
        proportions: 'Proportions'
      },
      titles: {
        deleteParty: 'Delete party',
        editParty: 'Edit party',
        deleteSpending: 'Delete spending',
        editSpending: 'Edit spending',
        deleteParticipant: 'Delete participant',
        editParticipant: 'Edit participant'
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
        debts: 'To pay off debts, you need to make transactions',
        transactionPending: "{0} transfers {1} to {2}",
        transactionClosed: "{0} settled accounts with {1}"
      },
      participantsCard: {
        list: 'List of participants',
      },
      spendingsCard: {
        list: 'List of spendings'
      },
      splitType: {
        amount: 'Amount',
        equal: 'Equal',
        partition: 'Partition'
      },
      radio: {
        equal: 'Divide equally between everyone',
        notEqual: 'Divide differently'
      },
      questions: {
        deleteParty: 'Do you really want to delete the event?'
      },
      mainPage: {
        title: 'An easy way to split',
        titleAccent: 'joint spendings',
        heroSubtitle: `Planning a trip, a party, or rent? Create an event, record your purchases, and
        the service will calculate who owes whom and how much.`,
        heroNote: 'No tables or complicated formulas needed — just participants and their spendings.',
        heroCardRow1: 'You note who pays and for what.',
        heroCardRow2: 'The service splits the amount equally or in shares.',
        heroCardRow3: 'The end result is a clear list of who owes whom.',
        featureTitle1: 'Events and Participants',
        featureTitle2: 'Convenient spendings tracking.',
        featureTitle3: 'Transparent calculations.',
        featureDescription1: `Create separate events for trips, parties, repairs, and any general expenses.'
        Add participants and monitor their balance.`,
        featureDescription2: `Record purchases in a couple of clicks: who paid, the amount, and the split type—equal 
        or individual shares.`,
        featureDescription3: `The service calculates who ultimately owes what. All you have to do is agree on transfers,
        without disputes or "who paid for what".`,
      },
      errors: {
        emailRequired: 'E-mail is required',
        emailIncorrect: 'E-mail is incorrect',
        passwordRequired: 'Password is required',
        passwordIncorrect: 'Min length of password is 6',
        participantNameRequired: 'Participant name is required',
        participantNameUnique: 'Participant name is already exists',
        nameRequired: 'Name is required'
      }
    },

    ru: {
      headers: {
        authorization: 'Авторизация',
        myParties: 'Мои события',
        participants: 'Участники',
        spendings: 'Расходы',
        transactions: 'Кто кому должен?',
        statistics: 'Статистика участников по расходам',
        createParty: 'Создать событие',
        updateParty: 'Обновить событие',
        deleteParty: 'Удалить событие',
        createSpending: 'Создать расход'
      },
      buttons: {
        signIn: 'Войти',
        signUp: 'Зарегистрироваться',
        createParty: 'Создать событие',
        updateParty: 'Обновить событие',
        update: 'Обновить',
        signOut: 'Выйти',
        addParticipant: 'Добавить участника',
        addSpending: 'Добавить расход',
        yes: 'Да',
        no: 'Нет'
      },
      labels: {
        email: 'E-mail',
        password: 'Пароль',
        total: 'Всего',
        participants: 'Участники',
        participantsAlt: 'участник(ов)',
        participantName: "Имя участника",
        spendings: 'расходы',
        spendingsAlt: 'расход(ов)',
        payer: 'Кто платил?',
        spendingName: 'За что?',
        description: 'Описание',
        name: 'Название',
        amount: 'Сумма',
        all: 'Все',
        selected: 'Выбраны',
        proportions: 'Доли'
      },
      titles: {
        deleteParty: 'Удалить событие',
        editParty: 'Редактировать событие',
        deleteSpending: 'Удалить расход',
        editSpending: 'Редактировать расход',
        deleteParticipant: 'Удалить участника',
        editParticipant: 'Редактировать участника'
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
        transactionPending: "{0} переводит {1} на счет {2}",
        transactionClosed: "{0} рассчитался(лась) с {1}"
      },
      participantsCard: {
        list: 'Список участников'
      },
      spendingsCard: {
        list: 'Список расходов'
      },
      splitType: {
        amount: 'Сумма',
        equal: 'Поровну',
        partition: 'В пропорции'
      },
      radio: {
        equal: 'Разделить поровну между всеми',
        notEqual: 'Разделить по-другому'
      },
      questions: {
        deleteParty: 'Вы действительно хотите удалить событие?'
      },
      mainPage: {
        title: 'Легкий способ разделить',
        titleAccent: 'совместные расходы',
        heroSubtitle: `Планируете поездку, вечеринку или квартирные траты? Создайте событие, фиксируйте покупки и 
        сервис посчитает, кто кому и сколько должен.`,
        heroNote: 'Не нужно таблиц и сложных формул — только участники и их траты.',
        heroCardRow1: 'Вы отмечаете, кто платит и за что',
        heroCardRow2: 'Сервис делит сумму поровну или по долям',
        heroCardRow3: 'В итоге — понятный список, кто кому должен',
        featureTitle1: 'События и участники',
        featureTitle2: 'Удобный учет расходов',
        featureTitle3: 'Прозрачные расчёты',
        featureDescription1: `Создавайте отдельные события под поездки, вечеринки, ремонты и любые общие затраты. 
        Добавляйте участников и следите за их балансом.`,
        featureDescription2: `Фиксируйте покупки в пару кликов: кто заплатил, сумму и тип разделения — поровну или по 
        индивидуальным долям.`,
        featureDescription3: `Сервис считает, кто в итоге должен и кому. Вам остаётся только договориться о переводах, 
        без споров и «кто платил за что».`,
      },
      errors: {
        emailRequired: 'E-mail обязателен',
        emailIncorrect: 'Введите корректный e-mail',
        passwordRequired: 'Пароль обязателен',
        passwordIncorrect: 'Пароль должен быть минимум 6 символов',
        participantNameRequired: 'Имя участника не может быть пустым',
        participantNameUnique: 'Участник с таким именем уже существует',
        nameRequired: 'Имя обязательно'
      }
    }
  }
})