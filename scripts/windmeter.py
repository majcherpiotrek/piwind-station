import RPi.GPIO as GPIO
from time import sleep
import yaml

CONF_FILE_NAME = './scripts/config.yml'
MISSING_CONF_ERROR = "CRITICAL ERROR: Missing the 'config.yml' configuration file!"
READING_CONF_ERROR = "CRITICAL ERROR: Cannot read the 'config.yml' configuration file!"
HSF_KEY = "high_wind_speed_factor"
LSF_KEY = "low_wind_speed_factor"
RADIUS_KEY = "windmeter_radius"
MEASUREMENT_TIME_KEY = "measurement_time"

GPIO.setmode(GPIO.BCM)
GPIO.setup(10, GPIO.IN, pull_up_down = GPIO.PUD_DOWN)

revs = 0
edges = 0

def __get_config():
    config_file = None
    try:
        config_file = open(CONF_FILE_NAME, 'r')
    except IOError:
        sys.stderr.write(MISSING_CONF_ERROR)
        exit(1)

    config = None
    try:
        with config_file:
            config = yaml.load(config_file)
    except yaml.YAMLError:
        sys.stderr.write(READING_CONF_ERROR)
        exit(1)

    config_file.close()

    return config


def __count_revolutions(channel):
	global revs
	global edges
	edges += 1
	if edges%2 == 0:
		revs += 1
		
		
config = __get_config()
measurement_time = config[MEASUREMENT_TIME_KEY]
lsf = config[LSF_KEY]
hsf = config[HSF_KEY]

try:
    GPIO.add_event_detect(10, GPIO.FALLING, callback=__count_revolutions)
    sleep(measurement_time)
    GPIO.cleanup()
    rps = revs / measurement_time
    v_kmh = lsf / (1 + rps) + hsf * rps
    v_ms = v_kmh / 3.6
    print('measurement_time_seconds: %d' %measurement_time)
    print('avg_windspeed_mps: %f' %v_ms)
    exit(0)
except ValueError as e:
    print('Error while fetching windsensor data: ' )
    exit(1)

