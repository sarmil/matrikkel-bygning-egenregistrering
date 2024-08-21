package no.kartverket.matrikkel.bygning.models.requests

import kotlinx.serialization.Serializable
import no.kartverket.matrikkel.bygning.models.kodelister.AvlopKode
import no.kartverket.matrikkel.bygning.models.kodelister.EnergikildeKode
import no.kartverket.matrikkel.bygning.models.kodelister.OppvarmingKode
import no.kartverket.matrikkel.bygning.models.kodelister.VannforsyningKode

@Serializable
data class ByggeaarRegistrering(
    val byggeaar: Int,
)

@Serializable
data class BruksarealRegistrering(
    val bruksareal: Double,
)

@Serializable
data class VannforsyningRegistrering(
    val vannforsyning: VannforsyningKode,
)

@Serializable
data class AvlopRegistrering(
    val avlop: AvlopKode,
)

@Serializable
data class EnergikildeRegistrering(
    val energikilder: List<EnergikildeKode>,
)

@Serializable
data class OppvarmingRegistrering(
    val oppvarminger: List<OppvarmingKode>,
)

@Serializable
data class BygningRegistrering(
    val bruksareal: BruksarealRegistrering?,
    val byggeaar: ByggeaarRegistrering?,
    val vannforsyning: VannforsyningRegistrering?,
    val avlop: AvlopRegistrering?
)

@Serializable
data class BruksenhetRegistrering(
    val bruksenhetId: Long,
    val bruksareal: BruksarealRegistrering?,
    val energikilde: EnergikildeRegistrering?,
    val oppvarming: OppvarmingRegistrering?
)

@Serializable
data class EgenregistreringRequest(
    val bygningRegistrering: BygningRegistrering, val bruksenhetRegistreringer: List<BruksenhetRegistrering>?
)
