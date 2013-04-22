package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deadshot Minotaur")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR})
@ManaCost("3RG")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class DeadshotMinotaur extends Card
{
	public static final class ClayPigeon extends EventTriggeredAbility
	{
		public ClayPigeon(GameState state)
		{
			super(state, "When Deadshot Minotaur enters the battlefield, it deals 3 damage to target creature with flying.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature with flying");
			this.addEffect(permanentDealDamage(3, targetedBy(target), "Deadshot Minotaur deals 3 damage to target creature with flying."));
		}
	}

	public DeadshotMinotaur(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		this.addAbility(new ClayPigeon(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(RG)"));
	}
}
