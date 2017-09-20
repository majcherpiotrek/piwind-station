"""

This file stores the utility fuctions for retreving the data from the temperature sensors

"""
import yaml
import re
import sys

CONF_FILE_NAME = './scripts/config.yml'
MISSING_CONF_ERROR = "CRITICAL ERROR: Missing the 'config.yml' configuration file!"
READING_CONF_ERROR = "CRITICAL ERROR: Cannot read the 'config.yml' configuration file!"
THERM_READ_ERROR = 'ERROR: Cannot open the thermometer slave file'
NO_DATA = '9999'

INTERNAL_THERM_KEY = 'internal_therm_path'
EXTERNAL_THERM_KEY = 'external_therm_path'

INTERNAL_TEMP_OUTPUT_KEY = 'internal_temp'
EXTERNAL_TEMP_OUTPUT_KEY = 'external_temp'


def __parse_temp_data(data):
    temp = re.search('t=[0-9]*', data).group()
    temp = temp[2:]
    temp = float(temp) / 1000
    temp = str(temp)
    return temp


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


def __get_temperature(therm_key):
    config = __get_config()
    therm_path = config[therm_key]

    # Read the internal thermometer data
    try:
        therm_file = open(therm_path, 'r')
        therm_data = therm_file.read()

        try:
            temp = __parse_temp_data(therm_data)
        except AttributeError:
            temp = NO_DATA

        therm_file.close()
    except IOError:
        sys.stderr.write(THERM_READ_ERROR)
        exit(1)

    return temp

sys.stdout.write(__get_temperature(INTERNAL_THERM_KEY))
exit(0)

