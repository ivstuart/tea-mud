package com.ivstuart.tmud.constants;

/**
 * Enum is not used. Please delete this class.
 */
public class RoomEnums {

	public enum RoomFlag {
		DARK, DEATH, NO_MOB, INDOORS, PEACEFUL, SOUND_PROOF, NO_TRACK, NO_MAGIC, TUNNEL, PRIVATE, GOD_ROOM, HOUSE
	}

	public enum SectorType {
		INSIDE, CITY, FIELD, FOREST, HILLS, MOUNTAIN, WATER_SWIM, WATER_NOSWIM, UNDERWATER, FLYING
	}

}

/**
 * 
 * 1 a SPEC This flag must be set on mobiles which have special procedures
 * written in C. In addition to setting this bit, the specproc must be assigned
 * in spec_assign.c, and the specproc itself must (of course) must be written.
 * See the section on Special Procedures in the file coding.doc for more
 * information.
 * 
 * 2 b SENTINEL Mobiles wander around randomly by default; this bit should be
 * set for mobiles which are to remain stationary.
 * 
 * 4 c SCAVENGER The mob should pick up valuables it finds on the ground. More
 * expensive items will be taken first.
 * 
 * 8 d ISNPC Reserved for internal use. Do not set.
 * 
 * 16 e AWARE Set for mobs which cannot be backstabbed. Replaces the
 * ACT_NICE_THIEF bit from Diku Gamma.
 * 
 * 32 f AGGRESSIVE Mob will hit all players in the room it can see. See also the
 * WIMPY bit.
 * 
 * 64 g STAY_ZONE Mob will not wander out of its own zone -- good for keeping
 * your mobs as only part of your own area.
 * 
 * 128 h WIMPY Mob will flee when being attacked if it has less than 20% of its
 * hit points. If the WIMPY bit is set in conjunction with any of the forms of
 * the AGGRESSIVE bit, the mob will only attack mobs that are unconscious
 * (sleeping or incapacitated).
 * 
 * 256 i AGGR_EVIL Mob will attack players that are evil-aligned.
 * 
 * 512 j AGGR_GOOD Mob will attack players that are good-aligned.
 * 
 * 1024 k AGGR_NEUTRAL Mob will attack players that are neutrally aligned.
 * 
 * 2048 l MEMORY Mob will remember the players that initiate attacks on it, and
 * initiate an attack on that player if it ever runs into him again.
 * 
 * 4096 m HELPER The mob will attack any player it sees in the room that is
 * fighting with a mobile in the room. Useful for groups of mobiles that travel
 * together; i.e. three snakes in a pit, to force players to fight all three
 * simultaneously instead of picking off one at a time.
 * 
 * 8192 n NOCHARM Mob cannot be charmed.
 * 
 * 16384 o NOSUMMON Mob cannot be summoned.
 * 
 * 32768 p NOSLEEP Sleep spell cannot be cast on mob.
 * 
 * 65536 q NOBASH Large mobs such as trees that cannot be bashed.
 * 
 * 131072 r NOBLIND Mob cannot be blinded.
 * 
 * 1 a BLIND Mob is blind. 2 b INVISIBLE Mob is invisible. 4 c DETECT_ALIGN Mob
 * is sensitive to the alignment of others. 8 d DETECT_INVIS Mob can see
 * invisible characters and objects. 16 e DETECT_MAGIC Mob is sensitive to
 * magical presence. 32 f SENSE_LIFE Mob can sense hidden life. 64 g WATERWALK
 * Mob can traverse unswimmable water sectors. 128 h SANCTUARY Mob is protected
 * by sanctuary (half damage). 256 i GROUP Reserved for internal use. Do not
 * set. 512 j CURSE Mob is cursed. 1024 k INFRAVISION Mob can see in dark. 2048
 * l POISON Reserved for internal use. Do not set. 4096 m PROTECT_EVIL Mob is
 * protected from evil characters. 8192 n PROTECT_GOOD Mob is protected from
 * good characters. 16384 o SLEEP Reserved for internal use. Do not set. 32768 p
 * NOTRACK Mob cannot be tracked. 65536 q UNUSED16 Unused (room for future
 * expansion). 131072 r UNUSED17 Unused (room for future expansion). 262144 s
 * SNEAK Mob can move quietly (room not informed). 524288 t HIDE Mob is hidden
 * (only visible with sense life). 1048576 u UNUSED20 Unused (room for future
 * expansion). 2097152 v CHARM Reserved for internal use. Do not set.
 **/

