# coding=utf-8
from __future__ import absolute_import

import octoprint.plugin
import flask
import os
import serial
import requests

from subprocess import call, Popen


class BlinkyControl(octoprint.plugin.StartupPlugin,
					octoprint.plugin.TemplatePlugin,
					octoprint.plugin.EventHandlerPlugin,
					octoprint.plugin.BlueprintPlugin,
					octoprint.plugin.AssetPlugin):

	def issueCommand(self, cmd_str):
		try:
			url = "http://localhost:4567/blinky/processcommand"
			data_command = {"cmd": cmd_str}
			self._logger.info("Calling BlinkyControlWS with cmd: " + cmd_str)
			session = requests.Session()
			content = session.post(url, json=data_command)
			return content.content
		except:
			return

	# StartupPlugin
	def on_after_startup(self):
		self._logger.info("BlinkControl plugin lives")

	# TemplatePlugin
	def get_template_configs(self):
		return [
			dict(type="tab", custom_bindings=False)
		]

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/on", methods=["GET"])
	def on(self):
		return self.issueCommand("ON")

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/off", methods=["GET"])
	def off(self):
		return self.issueCommand("OFF")

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/white", methods=["GET"])
	def white(self):
		return self.issueCommand("WHITE")

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/red", methods=["GET"])
	def red(self):
		return self.issueCommand("RED")

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/green", methods=["GET"])
	def green(self):
		return self.issueCommand("GREEN")

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/blue", methods=["GET"])
	def blue(self):
		return self.issueCommand("BLUE")

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/lightblue", methods=["GET"])
	def lightblue(self):
		return self.issueCommand("LIGHTBLUE")

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/purple", methods=["GET"])
	def purple(self):
		return self.issueCommand("PURPLE")

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/yellow", methods=["GET"])
	def yellow(self):
		return self.issueCommand("YELLOW")

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/decreaseintensity", methods=["GET"])
	def decreaseintensity(self):
		return self.issueCommand("DECREASE_INTENSITY")

	# BlueprintPlugin
	@octoprint.plugin.BlueprintPlugin.route("/increaseintensity", methods=["GET"])
	def increaseintensity(self):
		return self.issueCommand("INCREASE_INTENSITY")

	def is_blueprint_protected(self):
		return False

	#### EVENTS #####################################################
	# EventHandlerPlugin
	def on_event(self, event, payload):
		command = self.get_settings_defaults.get([event])
		if command is not None:
			self.issueCommand(command)

	#### SETTINGS ###################################################
	def get_settings_defaults(self):
		return dict(
			Disconnected="OFF",
			Connected="WHITE",
			PrintCancelled="BLUE",
			PrintFailed="RED",
			PrintPaused="YELLOW",
			PrintResumed="WHITE",
			PrintDone="OFF"
		)

	def get_template_configs(self):
		return [
			dict(type="tab", custom_bindings=False),
		]

	def get_assets(self):
		return dict(
			css=["css/blinkycontrol.css"]
		)


__plugin_name__ = "BlinkyControl"
__plugin_implementation__ = BlinkyControl()
