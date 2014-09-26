package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deepwater Hypnotist")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.WIZARD})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class DeepwaterHypnotist extends Card
{
	public static final class DeepwaterHypnotistAbility0 extends EventTriggeredAbility
	{
		public DeepwaterHypnotistAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Deepwater Hypnotist becomes untapped, target creature an opponent controls gets -3/-0 until end of turn.");
			this.addPattern(inspired());

			SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), CreaturePermanents.instance());
			SetGenerator target = targetedBy(this.addTarget(enemyCreatures, "target creature an opponent controls"));
			this.addEffect(ptChangeUntilEndOfTurn(target, -3, -0, "Target creature an opponent controls gets -3/-0 until end of turn."));
		}
	}

	public DeepwaterHypnotist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Inspired \u2014 Whenever Deepwater Hypnotist becomes untapped, target
		// creature an opponent controls gets -3/-0 until end of turn.
		this.addAbility(new DeepwaterHypnotistAbility0(state));
	}
}
