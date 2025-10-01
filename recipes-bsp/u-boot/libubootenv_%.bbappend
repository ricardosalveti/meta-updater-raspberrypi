FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:rpi = " \
            file://fw_env.config \
            "

do_install:append:rpi () {
  install -d ${D}${sysconfdir}
  install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}
