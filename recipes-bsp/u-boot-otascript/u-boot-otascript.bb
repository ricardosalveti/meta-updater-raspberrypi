DESCRIPTION = "Boot script for launching OTA-enabled images on raspberrypi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS = "u-boot-mkimage-native"

COMPATIBLE_MACHINE = "raspberrypi"

SRC_URI = "file://boot.scr \
	   file://uEnv.txt"

S = "${UNPACKDIR}"
B = "${WORKDIR}/build"

inherit deploy

do_configure[noexec] = "1"

do_compile() {
    mkimage -A arm -O linux -T script -C none -a 0 -e 0 -n "Ostree boot script" -d ${UNPACKDIR}/boot.scr boot.scr
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 boot.scr ${DEPLOYDIR}/boot.scr-${MACHINE}-${PV}
    ln -sf boot.scr-${MACHINE}-${PV} ${DEPLOYDIR}/boot.scr-${MACHINE}
    ln -sf boot.scr-${MACHINE}-${PV} ${DEPLOYDIR}/boot.scr
    install -m 0644 ${UNPACKDIR}/uEnv.txt ${DEPLOYDIR}
}

addtask deploy after do_compile before do_build

PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "u-boot-default-script"
RPROVIDES:${PN} += "u-boot-default-script"
