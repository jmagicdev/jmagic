package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ogre's Cleaver")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({})
public final class OgresCleaver extends Card
{
	public static final class OgresCleaverAbility0 extends StaticAbility
	{
		public OgresCleaverAbility0(GameState state)
		{
			super(state, "Equipped creature gets +5/+0.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +5, +0));
		}
	}

	public OgresCleaver(GameState state)
	{
		super(state);

		// Equipped creature gets +5/+0.
		this.addAbility(new OgresCleaverAbility0(state));

		// Equip (5)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(5)"));
	}
}
