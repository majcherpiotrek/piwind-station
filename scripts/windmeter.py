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
CHANNEL_KEY = "windmeter_channel"
MIN_RPS_KEY = "min_rps"

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
channel = config[CHANNEL_KEY]
min_rps = config[MIN_RPS_KEY]

GPIO.setmode(GPIO.BCM)
GPIO.setup(channel, GPIO.IN, pull_up_down = GPIO.PUD_DOWN)

revs = 0
edges = 0

try:
    GPIO.add_event_detect(channel, GPIO.FALLING, callback=__count_revolutions)
    sleep(measurement_time)
    GPIO.cleanup()
    rps = revs / measurement_time
    if rps <= min_rps:
        v_kmh = 0
        v_ms = 0
    else:
        v_kmh = lsf / (1 + rps) + hsf * rps
        v_ms = v_kmh / 3.6
    print('%d' %measurement_time)
    print('%f' %v_ms)
    exit(0)
except ValueError as e:
    print('Error while fetching windsensor data: ' )
    exit(1)

