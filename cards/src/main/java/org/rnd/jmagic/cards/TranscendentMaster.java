package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Transcendent Master")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.AVATAR, SubType.HUMAN})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class TranscendentMaster extends Card
{
	public static final class ThisIsIndestructible extends StaticAbility
	{
		public ThisIsIndestructible(GameState state)
		{
			super(state, "Transcendent Master is indestructible.");
			this.addEffectPart(indestructible(This.instance()));
		}
	}

	public TranscendentMaster(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Level up (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(1)"));

		// LEVEL 6-11
		// 6/6
		// Lifelink
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 6, 11, 6, 6, "Lifelink", org.rnd.jmagic.abilities.keywords.Lifelink.class));

		// LEVEL 12+
		// 9/9
		// Lifelink
		// Transcendent Master is indestructible.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 12, 9, 9, "Lifelink; Transcendent Master is indestructible.", org.rnd.jmagic.abilities.keywords.Lifelink.class, ThisIsIndestructible.class));
	}
}
