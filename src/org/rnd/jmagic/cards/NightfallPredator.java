package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nightfall Predator")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class NightfallPredator extends AlternateCard
{
	public static final class NightfallPredatorAbility0 extends ActivatedAbility
	{
		public NightfallPredatorAbility0(GameState state)
		{
			super(state, "(R), (T): Nightfall Predator fights target creature.");
			this.setManaCost(new ManaPool("(R)"));
			this.costsTap = true;
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(fight(Union.instance(ABILITY_SOURCE_OF_THIS, ChosenTargetsFor.instance(Identity.instance(target), This.instance())), "Nightfall Predator fights target creature."));
		}
	}

	public NightfallPredator(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.setColorIndicator(Color.GREEN);

		// (R), (T): Nightfall Predator fights target creature. (Each deals
		// damage equal to its power to the other.)
		this.addAbility(new NightfallPredatorAbility0(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Nightfall Predator.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
