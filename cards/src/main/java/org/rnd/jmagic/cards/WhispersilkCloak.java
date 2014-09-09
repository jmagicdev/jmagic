package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Whispersilk Cloak")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@ColorIdentity({})
public final class WhispersilkCloak extends Card
{
	public static final class Untouchable extends StaticAbility
	{
		public Untouchable(GameState state)
		{
			super(state, "Equipped creature can't be blocked and has shroud.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());

			this.addEffectPart(unblockable(equippedCreature));

			this.addEffectPart(addAbilityToObject(equippedCreature, org.rnd.jmagic.abilities.keywords.Shroud.class));
		}
	}

	public WhispersilkCloak(GameState state)
	{
		super(state);

		this.addAbility(new Untouchable(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
