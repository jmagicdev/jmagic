package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Daybreak Ranger")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ARCHER, SubType.WEREWOLF})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
@BackFace(NightfallPredator.class)
public final class DaybreakRanger extends Card
{
	public static final class DaybreakRangerAbility0 extends ActivatedAbility
	{
		public DaybreakRangerAbility0(GameState state)
		{
			super(state, "(T): Daybreak Ranger deals 2 damage to target creature with flying.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature with flying"));

			this.addEffect(permanentDealDamage(2, target, "Daybreak Ranger deals 2 damage to target creature with flying."));
		}
	}

	public DaybreakRanger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (T): Daybreak Ranger deals 2 damage to target creature with flying.
		this.addAbility(new DaybreakRangerAbility0(state));

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Daybreak Ranger.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
