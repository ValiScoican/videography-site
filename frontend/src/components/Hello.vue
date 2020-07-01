
<template>
  <div>
    <v-toolbar style="background: silver">
      <img src="./assets/VS_LOGO.png" alt="Logo" height="30" style="margin-right: 10px" />
      <v-toolbar-title>Vali Scoican</v-toolbar-title>
      <div class="flex-grow-1"></div>
      <!-- MANAGE BUTTON IF ADMINISTRATOR LOGGED -->
      <v-row justify="center">
        <v-dialog v-model="manage" max-width="690">
          <v-card>
            <v-card-title class="headline">Pending sessions:</v-card-title>
            <v-card-text>
              <div v-if="items.length === 0">
                <span class="subheading">No new events for today. 😒</span>
                <br />
                <span class="subheading">See you later! 🐱‍👤</span>
              </div>
              <v-list-item-group color="primary">
                <v-list-item v-for="(item, i) in items" :key="i">
                  <v-list-item-content>
                    <v-list-item-title v-html="item.name"></v-list-item-title>
                    <v-list-item-subtitle v-html="item.number"></v-list-item-subtitle>
                  </v-list-item-content>
                  <v-btn class="ma-2" text @click="aprove(item)">Aprove</v-btn>
                  <v-btn class="ma-2" text @click="decline(item)">Decline</v-btn>
                </v-list-item>
              </v-list-item-group>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn text @click="manage = false">Close</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>

      <!-- CALENDAR ICON -->
      <v-btn
        id="manage"
        text
        class="ma-2"
        v-if="user && user.role === 'ADMIN'"
        @click="manage = true"
      >Manage</v-btn>
      <v-btn class="ma-2" text icon @click="$vuetify.goTo('#calendar', {duration: 1300})">
        <v-icon>mdi-calendar-today</v-icon>
      </v-btn>

      <!-- IN OUT SECTION -->
      <div class v-if="user === null">
        <!-- LOGIN BUTTON - SIGN IN-->
        <v-btn text to="/login">{{ $t('login') }}</v-btn>
        <span class="pa-1"></span>

        <!-- REGISTRATION - SIGN UP -->
        <v-btn outlined text to="/registration">{{ $t('register') }}</v-btn>
      </div>
      <div class v-else>
        <span>{{ user.username }}</span>

        <!-- LOGOUT BUTTON -->
        <v-btn class="ma-2" text icon @click="logout">
          <v-icon>mdi-exit-to-app</v-icon>
        </v-btn>
      </div>

      <v-toolbar-items></v-toolbar-items>
    </v-toolbar>

    <v-content>
      <!-- ENTIRE PAGE WITH PARALLAX AND CALENDAR  -->
      <section id="header-parallax">
        <v-parallax src="./assets/bg1.jpg" height="1100">
          <v-layout column align-center justify-center class="white--text">
            <img
              data-aos="fade-down"
              data-aos-delay="300"
              src="./assets/VS_LOGO.png"
              alt="Logo"
              height="200"
            />
            <h1
              class="white--text mb-2 di 1 text-center"
              data-aos="fade-up"
              data-aos-delay="500"
              data-aos-once="true"
            >Vali Scoican</h1>
            <div class="subheading mb-4 text-center">
              <p data-aos="fade-up" data-aos-delay="400">Videograf | Powered by iStudio Craiova</p>
            </div>
          </v-layout>
        </v-parallax>
      </section>

      <!-- SECTIUNE POZA CERC ALBASTRU -->
      <section style="background: #1A1A1A">
        <v-layout row wrap align-center>
          <v-flex xs12>
            <v-container grid-list-xl>
              <v-layout row wrap justify-center class="my-12">
                <v-flex xs12 sm4 offset-sm1>
                  <v-card flat elevation="24" class="transparent" color="#385F73" dark>
                    <v-card-title primary-title align="center" justify="center">
                      <div
                        class="headline"
                        data-aos="fade-up"
                        data-aos-delay="200"
                        data-aos-duration="1000"
                        data-aos-once="true"
                      >Vali Scoican</div>
                    </v-card-title>
                    <v-card-text
                      data-aos="fade-up"
                      data-aos-delay="150"
                      data-aos-once="true"
                      data-aos-duration="800"
                    >Sunt fericit să aflu că vrei să mă cunoști. Nu uita că pentru o colaborare reușită este necesar să rezonezi cu omul alături de care vei petrece cel mai mult timp din nuntă. Videograful.</v-card-text>
                    <v-card-text data-aos="fade-down" data-aos-delay="350" data-aos-once="true">
                      Un videograf de nuntă trebuie sa fie omul alături de care să te simți „TU”!
                      Videograful de nuntă este exact omul care îți oferă siguranța că absolut tot va decurge exact cum ți-ai planificat. El este omul care nu intervine din 5 în 5 minute și te roagă să-ți repeți miscările ca să tragă cadrul din nou. Este un profesionist care este prezent și dedicat căruia îi pasă de tine. Am ajuns la nivelul actual în mai puțin de trei ani și sunt recunoscător persoanelor fără de care mi-ar fi luat mult mai mult timp să ajung aici.
                    </v-card-text>
                  </v-card>
                </v-flex>
                <v-flex xs12 sm4>
                  <v-card flat class="transparent" color="#385F73" dark>
                    <v-card-title primary-title class="layout justify-center">
                      <img
                        data-aos="fade-right"
                        data-aos-once="true"
                        data-aos-delay="150"
                        data-aos-easing="ease-in"
                        data-aos-duration="700"
                        src="./assets/profil.png"
                        alt="profile-picture"
                        style="margin: 30px"
                        height="400"
                      />
                    </v-card-title>
                  </v-card>
                </v-flex>
                <div column align-center justify-center class="white--text ml-6">
                  <p
                    data-aos="fade-up"
                    data-aos-delay="150"
                    data-aos-once="true"
                    data-aos-duration="1000"
                  >În continuare îți voi povesti câteva despre mine</p>
                </div>
              </v-layout>
            </v-container>
          </v-flex>
        </v-layout>
      </section>

      <!-- SECTIUNE DESPRE MINE | POZA STANGA, DREAPTA TEXT  -->
      <section>
        <v-parallax src="./assets/bg2.jpg" height="1000">
          <v-layout row wrap align-center class="my-12">
            <v-layout row wrap align-center>
              <v-flex xs12>
                <!-- <section> -->
                <v-container grid-list-xl>
                  <v-layout row wrap justify-center class="my-12">
                    <v-flex xs12 sm4>
                      <v-card elevation="24" class="transparent" color="#385F73" dark>
                        <v-card-title primary-title class="layout justify-center">
                          <img
                            data-aos="zoom-in"
                            data-aos-once="true"
                            data-aos-delay="50"
                            data-aos-easing="ease-in"
                            data-aos-duration="700"
                            src="./assets/bg4.jpg"
                            height="250"
                          />
                        </v-card-title>
                        <v-card-text
                          data-aos="fade-up"
                          data-aos-once="true"
                          data-aos-delay="150"
                          data-aos-duration="700"
                          data-aos-easing="ease-in"
                        >Nu sunt aici să îți spun că am „15 ani de experiență pe piața muncii” și nici nu ar avea rost. Persoanele care caută genul ăsta de „fotograf” sunt excluse din start din target-ul meu țintă. Știi de ce? Pentru că sunt convins că oamenii cărora mă adresez pun accent pe calitatea serviciilor mele, pe fotografii în special. Pun accent si pe omul din spatele aparatului de fotografiat, pe întreaga experiență care, fie vorba între noi, de-abia așteaptă să înceapă.</v-card-text>
                      </v-card>
                    </v-flex>
                    <v-flex xs12 sm4 offset-sm1>
                      <v-card flat elevation="24" class="transparent" color="#385F73" dark>
                        <v-card-title primary-title align="center" justify="center">
                          <div
                            data-aos="fade-left"
                            class="headline" 
                            data-aos-once="true"
                            data-aos-delay="150"
                            data-aos-duration="500"
                            data-aos-easing="ease-in"
                          >Într-o permanentă educație.</div>
                          <br>
                          <br>
                        </v-card-title>
                        <v-card-text
                          data-aos="fade-right"
                          data-aos-once="true"
                          data-aos-delay="150"
                          data-aos-duration="900"
                          data-aos-easing="ease-in"
                        >Workshop-uri și conferințe de videografie, training-uri despre cum să aduci valoare serviciilor tale, cursuri online despre marketing, cărți despre vânzări și educație antreprenorială, sunt doar câteva din elementele din care rezultă un videograf de succes. Desigur, nu orice videograf a fost nevoit să învețe lucrurile astea, pentru că dacă ești suficient de „vechi” pe piață, ești recunoscut ca un fotograf „decent” și ți se va duce vorba din om în om, adică vei ajunge cunoscut cu trecerea timpului. Eu nu am putut să accept varianta asta, în care m-aș fi mulțumit cu nivelul meu și aș fi stagnat până ar fi trecut suficient timp ca oamenii să fi auzit de Scoican Valentin ca videograf de nuntă.</v-card-text>
                        <v-card-text
                          data-aos="fade-left"
                          data-aos-once="true"
                          data-aos-delay="150"
                          data-aos-duration="700"
                          data-aos-easing="ease-in"
                        >Deoarece doar 11% din persoanele care vizitează de obicei această pagină ajung să citească până aici, țin să îți mulțumesc din suflet și îți spun încă o dată cât de important este pentru mine că îți dorești să cunoști omul din spatele aparatului, și te rog să mă crezi, de-abia aștept să te cunosc!</v-card-text>
                      </v-card>
                    </v-flex>
                  </v-layout>
                </v-container>
                <!-- </section> -->
              </v-flex>
            </v-layout>
          </v-layout>
        </v-parallax>
      </section>

      <!-- middle cards -->
      <section style="background: #1A1A1A">
        <v-container grid-list-xl>
          <v-layout row wrap align-center>
            <!-- FIRST ICON - PALETTE -->
            <v-flex
              xs12
              md4
              data-aos="fade-right"
              data-aos-once="true"
              data-aos-delay="150"
              data-aos-duration="700"
              data-aos-easing="ease-in"
            >
              <v-card flat class="transparent" color="#385F73" dark>
                <v-card-text class="text-center">
                  <v-icon x-large class="blue--text text--lighten-2">mdi-palette</v-icon>
                </v-card-text>
                <v-card-title primary-title class="layout justify-center">
                  <div class="headline text-center">Alege</div>
                </v-card-title>
                <v-card-text
                  class="layout justify-center"
                >Alege să fii îndrăzneț și spune-mi orice idee trăznită care îți vine în minte.</v-card-text>
              </v-card>
            </v-flex>

            <!-- SECOND ICON - FAST -->
            <v-flex
              xs12
              md4
              data-aos="fade-up"
              data-aos-once="true"
              data-aos-delay="150"
              data-aos-duration="700"
              data-aos-easing="ease-in"
            >
              <v-card flat class="transparent" color="#385F73" dark>
                <v-card-text class="text-center">
                  <v-icon x-large class="blue--text text--lighten-2">mdi-flash</v-icon>
                </v-card-text>
                <v-card-title primary-title class="layout justify-center">
                  <div class="headline">Editare rapida</div>
                </v-card-title>
                <v-card-text
                  class="layout justify-center"
                >Primirea materialelor catre client se efectueaza in termen de 14 zile</v-card-text>
              </v-card>
            </v-flex>

            <!-- 3RD ICON - WRENCH -->
            <v-flex
              xs12
              md4
              data-aos="fade-left"
              data-aos-once="true"
              data-aos-delay="150"
              data-aos-duration="700"
              data-aos-easing="ease-in"
            >
              <v-card flat class="transparent" color="#385F73" dark>
                <v-card-text class="text-center">
                  <v-icon x-large class="blue--text text--lighten-2">mdi-wrench</v-icon>
                </v-card-text>
                <v-card-title primary-title class="layout justify-center">
                  <div class="headline text-center">Low Spec</div>
                </v-card-title>
                <v-card-text
                  class="layout justify-center"
                >Pentru clienții cărora materialul trimis de noi rulează în reluare, contactați-ne, iar noi vom reface materialul cu exportul potrivit specificațiilor tehnice calculatorului dumneavoastră.</v-card-text>
              </v-card>
            </v-flex>
          </v-layout>
        </v-container>
      </section>
      <!-- end middle cards -->

      <!-- CALENDAR / PROGRAMARE-->
      <v-row justify="center">
        <v-dialog v-model="dialog" max-width="690">
          <v-card>
            <v-card-title class="headline">Alege data evenimentului tău</v-card-title>
            <v-card-text>
              <v-row justify="center">
                <v-text-field label="Full Name" :rules="rules" outlined v-model="name"></v-text-field>
              </v-row>
              <v-row justify="center">
                <v-text-field
                  label="Phone Number"
                  :rules="rules"
                  type="number"
                  outlined
                  v-model="number"
                ></v-text-field>
              </v-row>
              <v-row justify="center">
                <v-select v-model="eventType" :items="eventTypes" outlined label="Event type"></v-select>
              </v-row>
              <v-row justify="center">
                <v-col justify="center">
                  <v-date-picker v-model="picker" id="date-picker"></v-date-picker>
                </v-col>
                <v-col justify="center">
                  <v-time-picker v-model="time" type="month"></v-time-picker>
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" text @click="sendDate">Confirm</v-btn>
              <v-btn text @click="dialog = false">Cancel</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>
      <section id="calendar">
        <v-layout column wrap class="my-12" align-center>
          <v-flex xs12 sm4 class="my-4">
            <div class="text-center">
              <h2 class="headline">Alege data evenimentului tău</h2>
              <div class v-if="user !== null">
                <v-btn text @click.stop="dialog = true">Programare nouă</v-btn>
              </div>
              <div class v-else>
                <span id="cal-text">Pentru rezervare, te rog</span>
                <v-btn text to="/login">{{ $t('login') }}</v-btn>
              </div>
              <br />
              <Calendar :key="calendarKey" />
            </div>
          </v-flex>
        </v-layout>
      </section>
      <section>
        <v-layout column wrap class="my-12" align-center>
          <v-flex xs12 sm4 class="my-4">
            <div class="text-center">
              <div class v-if="user !== null">
                <div v-for="item in reviews" :key="item.id">
                  <v-list-item two-line>
                    <v-list-item-content>
                      <v-list-item-title>{{ item.username }}</v-list-item-title>
                      <v-list-item-subtitle>{{ item.content }}</v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </div>
              </div>
            </div>
          </v-flex>
        </v-layout>
      </section>
      <!-- END CALENDAR / PROGRAMARE-->

      <!-- FOOTER -->
      <v-footer color="grey darken-4">
        <v-layout row wrap align-center>
          <v-flex xs12>
            <section>
              <v-container grid-list-xl>
                <v-layout row wrap justify-center class="my-12">
                  <v-flex xs12 sm4>
                    <v-card flat class="transparent" color="#385F73" dark>
                      <v-card-title primary-title class="layout justify-center">
                        <a
                          class="headline"
                          href="http://www.istudio-foto.ro/"
                          target="_blank"
                        >iStudio Craiova</a>
                      </v-card-title>
                      <v-card-text>
                        <!-- Fotografie &amp; videografie de nuntă -->
                        iStudio-foto este conceput din 3 oameni devotați.
                        Facem ceea ce ne place și lucrăm cu drag și spor împreună cu cliețnii noștri.
                      </v-card-text>
                    </v-card>
                  </v-flex>
                  <v-flex xs12 sm4 offset-sm1>
                    <v-card flat class="transparent" color="#385F73" dark>
                      <v-card-title primary-title align="center" justify="center">
                        <div class="headline">Contactează-mă</div>
                      </v-card-title>
                      <v-card-text>Simte-te liber de a luat legătura cu mine atât prin e-mail cât și telefonic pentru orice nelămurire. Răspund pe e-mail în cel mult o zi!</v-card-text>
                      <v-list class="transparent">
                        <v-list-item>
                          <v-list-item-action>
                            <v-icon class="blue--text text--lighten-2">mdi-phone</v-icon>
                          </v-list-item-action>
                          <v-list-item-content>
                            <v-list-item-title>0752-903-895</v-list-item-title>
                          </v-list-item-content>
                        </v-list-item>

                        <v-list-item>
                          <v-list-item-action>
                            <v-icon class="blue--text text--lighten-2">mdi-map-marker</v-icon>
                          </v-list-item-action>
                          <v-list-item-content>
                            <v-list-item-title>Craiova, România</v-list-item-title>
                          </v-list-item-content>
                        </v-list-item>

                        <v-list-item>
                          <v-list-item-action>
                            <v-icon class="blue--text text--lighten-2">mdi-email</v-icon>
                          </v-list-item-action>
                          <v-list-item-content>
                            <v-list-item-title>vali.scoican@yahoo.com</v-list-item-title>
                          </v-list-item-content>
                        </v-list-item>
                      </v-list>
                    </v-card>
                  </v-flex>
                </v-layout>
                <!-- COPYRIGHT & DATA ACTUALIZATA  -->
                <v-layout column align-center justify-center>
                  <div class="white--text ml-4">
                    <span>&copy; {{ new Date().getFullYear() }} ~ iStudio Craiova | Vali Scoican</span>
                  </div>
                </v-layout>
              </v-container>
            </section>
          </v-flex>
        </v-layout>
      </v-footer>
      <!-- END FOOTER -->
    </v-content>
  </div>
</template>


<script>
import VueEasySession from "vue-easysession";
import VueCookie from "vue-cookie";
import Vue from "vue";
import api from "./backend-api";
import Calendar from "./Calendar";

export default {
  data() {
    return {
      review: [],
      eventType: "Nunta",
      eventTypes: ["Nunta", "Botez", "Cununie", "Altceva..."],
      calendarKey: 0,
      items: [],
      manage: false,
      name: null,
      number: null,
      picker: new Date().toISOString().substr(0, 10),
      time: "11:15",
      dialog: false,
      user: null,
      rules: [
        value => !!value || "Required.",
        value => (value && value.length >= 10) || "Min 10 characters"
      ]
    };
  },
  components: {
    Calendar
  },
  methods: {
    sendDate() {
      if (this.name && this.number) {
        const pickedDateTime = `${this.picker}T${this.time}:00`;
        this.dialog = false;
        api.addEntry(this.name, this.number, pickedDateTime, this.eventType);
      }
    },
    changeLocale(code) {
      this.$i18n.locale = code;
      localStorage.setItem("locale", code);
    },
    logout() {
      const deviceId = Vue.cookie.get("deviceId");
      api.deleteTokenByDeviceId(deviceId).then(response => {
        if (response.status === 200) {
          this.user = null;
          let session = VueEasySession.getInstance();
          session.destroy();
        }
      });
    },
    getInvalidEvents() {
      api.getInvalidEvents().then(response => {
        const { data } = response;
        if (data) {
          this.items = data;
        }
      });
    },
    aprove(item) {
      api.validate(item.id).then(response => {
        if (response.status === 200) {
          this.getInvalidEvents();
          this.calendarKey++;
        }
      });
    },
    decline(item) {
      api.decline(item.id).then(response => {
        if (response.status === 200) {
          this.getInvalidEvents();
        }
      });
    }
  },
  created() {
    let session = VueEasySession.getInstance();
    let localUser = session.get("user");
    if (localUser) {
      this.user = localUser;
    }
    this.getInvalidEvents();
  }
};
</script>

<style scoped>
#date-picker {
  height: 100%;
}
#cal-text {
  margin-right: 1em;
}

a:link {
  text-decoration: none;
}

a:visited {
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

a:active {
  text-decoration: underline;
}
a {
  color: rgb(192, 6, 0);
}
</style>


   