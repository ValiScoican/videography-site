<template>
  <div>
    <v-sheet tile height="75" color="grey lighten-3" class="d-flex">
      <v-btn icon class="ma-2" @click="$refs.calendar.prev()">
        <v-icon>mdi-chevron-left</v-icon>
      </v-btn>
      <v-row justify="center">
        <v-select
          v-model="type"
          :items="types"
          dense
          outlined
          hide-details
          class="ma-2"
          label="type"
        ></v-select>
        <v-select
          v-model="weekday"
          :items="weekdays"
          dense
          outlined
          hide-details
          label="weekdays"
          class="ma-2"
        ></v-select>
      </v-row>
      <v-btn icon class="ma-2" @click="$refs.calendar.next()">
        <v-icon>mdi-chevron-right</v-icon>
      </v-btn>
    </v-sheet>
    <v-sheet height="600" width="65em">
      <v-calendar
        :key="calendar"
        ref="calendar"
        v-model="value"
        :weekdays="weekday"
        :type="type"
        :events="events"
        :event-overlap-mode="mode"
        :event-overlap-threshold="30"
        :event-color="getEventColor"
        @click:event="showEvent"
        @change="getEvents"
      ></v-calendar>
    </v-sheet>
    <v-dialog v-model="dialog" max-width="490">
      <v-card>
        <v-card-title>Event information.</v-card-title>

        <v-card-text v-if="selected !== null">
          <v-list-item two-line>
            <v-list-item-icon>
              <v-icon>mdi-chevron-right</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Name:</v-list-item-title>
              <v-list-item-subtitle>{{selected.name}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <v-list-item two-line>
            <v-list-item-icon>
              <v-icon>mdi-chevron-right</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Number:</v-list-item-title>
              <v-list-item-subtitle>{{selected.number}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <v-list-item two-line>
            <v-list-item-icon>
              <v-icon>mdi-chevron-right</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Type:</v-list-item-title>
              <v-list-item-subtitle>{{selected.eventType}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <v-list-item two-line>
            <v-list-item-icon>
              <v-icon>mdi-chevron-right</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Date:</v-list-item-title>
              <v-list-item-subtitle>{{selected.start}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="gray darken-1" text @click="dialog = false">Close</v-btn>

          <v-btn color="red darken-1" text @click="confirmationDialog = true">Delete event</v-btn>
        </v-card-actions>
      </v-card>
      <v-dialog v-model="confirmationDialog" max-width="290">
        <v-card>
          <v-card-title>Confirmation</v-card-title>

          <v-card-text>Are you sure you want to delete this event?</v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn color="green darken-1" text @click="confirmationDialog = false">No</v-btn>

            <v-btn color="red darken-1" text @click="deleteEvent">Yes</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-dialog>
  </div>
</template>

<script>
import api from "./backend-api";
import VueEasySession from "vue-easysession";

export default {
  data: () => ({
    calendar: 0,
    selected: null,
    dialog: false,
    confirmationDialog: false,
    type: "week",
    types: ["month", "week", "day"],
    weekday: [0, 1, 2, 3, 4, 5, 6],
    weekdays: [
      { text: "Sun - Sat", value: [0, 1, 2, 3, 4, 5, 6] },
      { text: "Mon - Sun", value: [1, 2, 3, 4, 5, 6, 0] },
      { text: "Mon - Fri", value: [1, 2, 3, 4, 5] },
      { text: "Mon, Wed, Fri", value: [1, 3, 5] }
    ],
    mode: "stack",
    weekday: [0, 1, 2, 3, 4, 5, 6],
    value: "",
    events: [],
    colors: [
      "blue",
      "indigo",
      "deep-purple",
      "cyan",
      "green",
      "orange",
      "grey darken-1"
    ]
  }),
  methods: {
    getEvents({ start, end }) {
      const events = [];

      api.getEvents().then(response => {
        const { data } = response;
        if (data) {
          data.forEach(d => {
            const date = new Date(d.date);
            // const end = new Date(date.getTime() + 8 * 900000);
            events.push({
              name: d.name,
              start: this.formatDate(date),
              // end: this.formatDate(end),
              color: this.colors[this.rnd(0, this.colors.length - 1)],
              id: d.id,
              eventType: d.eventType,
              number: d.number
            });
          });
          this.events = events;
        }
      });
    },
    getEventColor(event) {
      return event.color;
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
    formatDate(a) {
      return `${a.getFullYear()}-${a.getMonth() +
        1}-${a.getDate()} ${a.getHours()}:${a.getMinutes()}`;
    },
    showEvent({ nativeEvent, event }) {
      let session = VueEasySession.getInstance();
      let localUser = session.get("user");
      if (localUser && localUser.role === "ADMIN") {
        this.dialog = true;
        this.selected = event;
      }
    },
    deleteEvent() {
      const { id } = this.selected;
      api.decline(id).then(response => {
        if (response.status === 200) {
          this.selected = null;
          this.confirmationDialog = false;
          this.dialog = false;
          this.calendar++;
          this.$forceUpdate();
        }
      });
    }
  }
};
</script>