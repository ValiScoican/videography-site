import axios from 'axios'
import i18n from '@/plugins/i18n';

const AXIOS = axios.create({
    baseURL: `http://localhost:8080/api`,
    params: {
        locale: i18n.locale
    },
    timeout: 10000
});

export default {

    register(email, password) {
        return AXIOS.get(`/registration/` + email + '/' + password);
    },

    registrationConfirm(code) {
        return AXIOS.post(`/registrationConfirm/` + code);
    },

    login(email, password, deviceId) {
        return AXIOS.get(`/login/` + email + '/' + password + '/' + deviceId);
    },

    sendPasswordUpdateEmail(email) {
        return AXIOS.get(`/sendPasswordUpdateEmail/` + email);
    },

    updatePassword(code, password) {
        return AXIOS.get(`/updatePassword/` + code + '/' + password);
    },

    checkTokenExist(token) {
        return AXIOS.get(`/checkTokenExist/` + token);
    },

    deleteTokenByDeviceId(deviceId) {
        return AXIOS.post(`/deleteTokenByDeviceId/` + deviceId);
    },

    addEntry(name, number, date, eventType) {
        return AXIOS.post(`/add/` + name + '/' + number + '/' + date + '/' + eventType);
    },

    getEvents() {
        return AXIOS.get(`/events`);
    },

    getInvalidEvents() {
        return AXIOS.get(`/events/invalid`);
    },

    validate(id) {
        return AXIOS.get(`/aprove/${id}`);
    },

    decline(id) {
        return AXIOS.post(`/decline/${id}`);
    },

    getReviews() {
        return AXIOS.get(`/reviews`)
    },

    addReview(content, username) {
        return AXIOS.post(`/review/${encodeURIComponent(content)}/${username}`)
    },

    deleteReview(id) {
        return AXIOS.post(`/review/delete/${id}`)
    }
}


