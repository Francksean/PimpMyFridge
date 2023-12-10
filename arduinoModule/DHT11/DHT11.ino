#include <dht_nonblocking.h>
#include <ArduinoJson.h>

StaticJsonDocument<500> doc;

int relayPin = 9;

float internTemp;
float internhumidity;
float externTemp;
float externhumidity;

boolean isFridgeConsigned = false;
float wantedTemp;

static const int DHT_SENSOR_PIN_INTRIEUR = 2;
static const int DHT_SENSOR_PIN_EXTERIEUR = 7;

DHT_nonblocking dht_sensor_interieur(DHT_SENSOR_PIN_INTRIEUR, DHT_TYPE_22);
DHT_nonblocking dht_sensor_exterieur(DHT_SENSOR_PIN_EXTERIEUR, DHT_TYPE_22);

void setup() {
  pinMode(relayPin, OUTPUT);
  Serial.begin(9600);
}

static bool measure_environment_interieur(float *internTemp, float *internhumidity) {
  static unsigned long measurement_timestamp = millis();

  if (millis() - measurement_timestamp > 3000ul) {
    if (dht_sensor_interieur.measure(internTemp, internhumidity)) {
      measurement_timestamp = millis();
      return true;
    }
  }
  return false;
}

static bool measure_environment_exterieur(float *externTemp, float *externhumidity) {
  static unsigned long measurement_timestamp = millis();

  if (millis() - measurement_timestamp > 3000ul) {
    if (dht_sensor_exterieur.measure(externTemp, externhumidity)) {
      measurement_timestamp = millis();
      return true;
    }
  }
  return false;
}

void loop() {
  if (Serial.available() > 0) {
    if (measure_environment_exterieur(&externTemp, &externhumidity)) {
      doc["externTemp"] = externTemp;
      doc["externHum"] = externhumidity;
    }
    if (measure_environment_interieur(&internTemp, &internhumidity)) {
      if (internTemp > wantedTemp) {
        digitalWrite(relayPin, HIGH);
      } else {
        digitalWrite(relayPin, LOW);
      }
      doc["internTemp"] = internTemp;
      doc["internHum"] = internhumidity;
      wantedTemp = Serial.readStringUntil('\n').toFloat();
      doc["wantedTemp"] = wantedTemp ;
      isFridgeConsigned = true;

      serializeJson(doc, Serial);
      Serial.println("");
    }
  } else {
    if (measure_environment_exterieur(&externTemp, &externhumidity)) {
      doc["externTemp"] = externTemp;
      doc["externHum"] = externhumidity;
    }
    if (measure_environment_interieur(&internTemp, &internhumidity)) {
      if (internTemp > wantedTemp) {
        digitalWrite(relayPin, HIGH);
      } else {
        digitalWrite(relayPin, LOW);
      }
      doc["internTemp"] = internTemp;
      doc["internHum"] = internhumidity;
      if(isFridgeConsigned){
        doc["wantedTemp"] = wantedTemp ;
      }else{
        doc["wantedTemp"] = 15.0 ;

      }

      serializeJson(doc, Serial);
      Serial.println("");
    }
  }
}
