package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cursecatcher")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.WIZARD})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Cursecatcher extends Card
{
	public static final class CursecatcherAbility0 extends ActivatedAbility
	{
		public CursecatcherAbility0(GameState state)
		{
			super(state, "Sacrifice Cursecatcher: Counter target instant or sorcery spell unless its controller pays (1).");
			this.addCost(sacrificeThis("Cursecatcher"));

			Target target = this.addTarget(Intersect.instance(Spells.instance(), HasType.instance(Type.INSTANT, Type.SORCERY)), "target instant or sorcery spell");
			this.addEffect(counterTargetUnlessControllerPays("(1)", target));
		}
	}

	public Cursecatcher(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice Cursecatcher: Counter target instant or sorcery spell
		// unless its controller pays (1).
		this.addAbility(new CursecatcherAbility0(state));
	}
}
