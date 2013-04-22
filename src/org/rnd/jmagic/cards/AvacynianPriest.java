package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Avacynian Priest")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AvacynianPriest extends Card
{
	public static final class AvacynianPriestAbility0 extends ActivatedAbility
	{
		public AvacynianPriestAbility0(GameState state)
		{
			super(state, "(1), (T): Tap target non-Human creature.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			SetGenerator humans = HasSubType.instance(SubType.HUMAN);
			SetGenerator nonHumanCreature = RelativeComplement.instance(CreaturePermanents.instance(), humans);
			SetGenerator target = targetedBy(this.addTarget(nonHumanCreature, "target non-Human creature"));
			this.addEffect(tap(target, "Tap target non-Human creature."));
		}
	}

	public AvacynianPriest(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// (1), (T): Tap target non-Human creature.
		this.addAbility(new AvacynianPriestAbility0(state));
	}
}
