<template>
  <div class="fill-height">
    <v-progress-linear
      :active="inLoading"
      :indeterminate="inLoading"
      absolute
      color="deep-purple accent-4"
    ></v-progress-linear>

    <v-snackbar color="error" top v-model="snackbarError" :timeout="15000">
      <span>{{ message }}</span>
      <v-btn text @click="snackbarError = false">{{ $t('close') }}</v-btn>
    </v-snackbar>

    <v-snackbar color="success" top v-model="snackbarSuccess" :timeout="15000">
      <span>{{ message }}</span>
      <v-btn text @click="snackbarSuccess = false">{{ $t('close') }}</v-btn>
    </v-snackbar>

    <v-container fill-height fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
          <v-card class="elevation-12">
            <v-card-text>
              <h2>Please Login</h2>
              <br>
              <v-form>
                <v-text-field
                  ref="email"
                  :label="$t('email')"
                  name="email"
                  type="text"
                  outlined
                  :error-messages="emailErrors"
                  @input="$v.email.$touch()"
                  @blur="$v.email.$touch()"
                  required
                  v-model="email"
                ></v-text-field>
                <v-text-field
                  ref="password"
                  :label="$t('password')"
                  name="password"
                  type="password"
                  outlined
                  :error-messages="passwordErrors"
                  @input="$v.password.$touch()"
                  @blur="$v.password.$touch()"
                  required
                  :append-icon="'visibility_off'"
                  v-model="password"
                ></v-text-field>

                <v-layout row wrap class="ml-0 mr-0">
                  <v-checkbox v-model="remember" class="mt-0" :label="$t('remember')"></v-checkbox>
                  <v-spacer></v-spacer>
                  <router-link class="subtitle-1" to="/forgot-password">{{ $t('forgotPassword') }}</router-link>
                </v-layout>

                <v-btn
                  color="success"
                  style="min-height: 50px"
                  class="v-btn--block"
                  @click="login()"
                  :disabled="inProgress, inLoading"
                >{{ $t('login') }}</v-btn>
              </v-form>
            </v-card-text>
          </v-card>

          <v-card-text class="text-center">
            <div class="my-8 subtitle-1">
              <span v-once>
                {{ $t('dontHaveAccount') }}
                <router-link to="/registration">{{ $t('register') }}</router-link>
              </span>
              <br>
              <span v-once>
                <router-link to="/">Home page</router-link>
              </span>
            </div>
          </v-card-text>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import Vue from "vue";
import VueCookie from "vue-cookie";
import VueEasySession from "vue-easysession";
import api from "./backend-api";
import i18n from "../plugins/i18n";
import { required } from "vuelidate/lib/validators";

Vue.use(VueCookie);

export default {
  name: "Login",
  data() {
    return {
      email: "",
      password: "",
      remember: false,
      message: "",
      snackbarSuccess: false,
      snackbarError: false,
      inProgress: false,
      inLoading: false
    };
  },
  validations: {
    email: {
      required
    },
    password: {
      required
    }
  },
  computed: {
    emailErrors() {
      const errors = [];
      if (!this.$v.email.$dirty) return errors;
      !this.$v.email.required && errors.push(i18n.t("emailRequired"));
      return errors;
    },
    passwordErrors() {
      const errors = [];
      if (!this.$v.password.$dirty) return errors;
      !this.$v.password.required && errors.push(i18n.t("passwordRequired"));
      return errors;
    }
  },
  methods: {
    login() {
      this.inProgress = true;
      this.inLoading = true;
      if (this.$v.$invalid) {
        this.inProgress = false;
        this.inLoading = false;
      } else {
        let deviceId = Vue.cookie.get("deviceId");
        api
          .login(this.email, this.password, deviceId)
          .then(response => {
            if (response.data.success) {
              const session = VueEasySession.getInstance();
              session.start();
              session.set("token", response.data.accessToken.token);
              session.set("user", response.data.accessToken.user);
              Vue.cookie.set(
                "deviceId",
                response.data.accessToken.deviceId,
                730
              );
              if (this.remember)
                Vue.cookie.set("token", response.data.accessToken.token, 14);
              this.$router.push("/").catch(error => {
                console.log(error);
              });
            } else {
              this.inProgress = false;
              this.inLoading = false;
              this.message = response.data.message;
              this.snackbarError = true;
            }
          })
          .catch(error => {
            console.log(error);
            this.message = i18n.t("connectionLost");
            this.snackbarError = true;
            this.inProgress = false;
            this.inLoading = false;
          });
      }
    }
  },
  created() {
    if (this.$root.$data.message !== undefined) {
      this.message = this.$root.$data.message;
      this.snackbarSuccess = true;
    }
  }
};
</script>