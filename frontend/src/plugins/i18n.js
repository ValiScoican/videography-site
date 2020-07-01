import Vue from 'vue';
import VueI18n from 'vue-i18n';

Vue.use(VueI18n);

const messages = {
    'en': {
        application: 'Application',
        login: 'Sign in',
        register: 'Sign up',
        email: 'Email',
        enterYourEmail: 'Enter your email address',
        password: 'Password',
        confirmPassword: 'Confirm password',
        title: 'Title',
        projectName: 'Project name',
        companyName: 'Company name',
        getStarted: 'Get started',
        companyInfo: 'Company info',
        contacts: 'Contact us',
        remember: 'Remember me?',
        forgotPassword: 'Forgot password?',
        dontHaveAccount: 'Don\'t have an account?',
        haveAccount: 'Already have an account?',
        emailRequired: 'Email is required.',
        emailNotValid: 'Email is not valid.',
        passwordRequired: 'Password is required.',
        passwordFormat: 'Password should contains min 8 characters including a number and a uppercase letter.',
        passwordMatch: 'The password you entered don\'t match.',
        connectionLost: 'The connection to the server has been lost.',
        sendPasswordUpdateEmail: 'Send password update email',
        updatePassword: 'Update password',
        returnToLogin: 'Return to login',
        close: 'Close',
        home: 'Home',
        contact: 'Contacts',
        logout: 'Logout',
        language: "Language",
        success: "Success",
        error: "Error"
    },
    
};

const i18n = new VueI18n({
    locale: localStorage.getItem('locale') || navigator.language.split('-')[0],
    fallbackLocale: 'en',
    messages,
});

export default i18n;