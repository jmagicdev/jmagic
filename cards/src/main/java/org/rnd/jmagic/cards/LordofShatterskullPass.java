package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lord of Shatterskull Pass")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.SHAMAN})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class LordofShatterskullPass extends Card
{
	public static final class BoneCracker extends EventTriggeredAbility
	{
		public BoneCracker(GameState state)
		{
			super(state, "Whenever Lord of Shatterskull Pass attacks, it deals 6 damage to each creature defending player controls.");

			this.addPattern(whenThisAttacks());

			this.addEffect(permanentDealDamage(6, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS))), "It deals 6 damage to each creature defending player controls."));
		}
	}

	public LordofShatterskullPass(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Level up (1)(R)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(1)(R)"));

		// LEVEL 1-5
		// 6/6
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 5, 6, 6));

		// LEVEL 6+
		// 6/6
		// Whenever Lord of Shatterskull Pass attacks, it deals 6 damage to each
		// creature defending player controls.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 6, 6, 6, "\"Whenever Lord of Shatterskull Pass attacks, it deals 6 damage to each creature defending player controls.\"", BoneCracker.class));
	}
}
