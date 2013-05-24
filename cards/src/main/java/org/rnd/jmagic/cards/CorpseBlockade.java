package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Corpse Blockade")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class CorpseBlockade extends Card
{
	public static final class CorpseBlockadeAbility1 extends ActivatedAbility
	{
		public CorpseBlockadeAbility1(GameState state)
		{
			super(state, "Sacrifice another creature: Corpse Blockade gains deathtouch until end of turn.");

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addCost(sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature"));

			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Deathtouch.class, "Corpse Blockade gains deathtouch until end of turn."));
		}
	}

	public CorpseBlockade(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Sacrifice another creature: Corpse Blockade gains deathtouch until
		// end of turn.
		this.addAbility(new CorpseBlockadeAbility1(state));
	}
}
