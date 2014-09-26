package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bloodsoaked Champion")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class BloodsoakedChampion extends Card
{
	public static final class BloodsoakedChampionAbility1 extends ActivatedAbility
	{
		public BloodsoakedChampionAbility1(GameState state)
		{
			super(state, "Raid \u2014 (1)(B): Return Bloodsoaked Champion from your graveyard to the battlefield. Activate this ability only if you attacked with a creature this turn.");
			this.setManaCost(new ManaPool("(1)(B)"));

			this.addEffect(putOntoBattlefield(ABILITY_SOURCE_OF_THIS, "Return Bloodsoaked Champion from your graveyard to the battlefield."));

			this.activateOnlyFromGraveyard();

			state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
			this.addActivateRestriction(Raid.instance());
		}
	}

	public BloodsoakedChampion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Bloodsoaked Champion can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));

		// Raid \u2014 (1)(B): Return Bloodsoaked Champion from your graveyard
		// to the battlefield. Activate this ability only if you attacked with a
		// creature this turn.
		this.addAbility(new BloodsoakedChampionAbility1(state));
	}
}
