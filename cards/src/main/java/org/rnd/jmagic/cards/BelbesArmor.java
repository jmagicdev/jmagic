package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Belbe's Armor")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class BelbesArmor extends Card
{
	public static final class Reinforce extends ActivatedAbility
	{
		public Reinforce(GameState state)
		{
			super(state, "(X), (T): Target creature gets -X/+X until end of turn.");

			this.setManaCost(new ManaPool("X"));
			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			SetGenerator valueX = ValueOfX.instance(This.instance());
			SetGenerator negativeX = Subtract.instance(numberGenerator(0), valueX);

			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), negativeX, valueX, "Target creature gets -X/+X until end of turn."));
		}
	}

	public BelbesArmor(GameState state)
	{
		super(state);

		this.addAbility(new Reinforce(state));
	}
}
