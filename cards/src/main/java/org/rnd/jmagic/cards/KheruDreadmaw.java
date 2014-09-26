package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kheru Dreadmaw")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.CROCODILE})
@ManaCost("4B")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class KheruDreadmaw extends Card
{
	public static final class KheruDreadmawAbility1 extends ActivatedAbility
	{
		public KheruDreadmawAbility1(GameState state)
		{
			super(state, "(1)(G), Sacrifice another creature: You gain life equal to the sacrificed creature's toughness.");
			this.setManaCost(new ManaPool("(1)(G)"));

			SetGenerator anotherCreature = RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS);
			EventFactory sacrifice = sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature");
			this.addCost(sacrifice);

			SetGenerator amount = ToughnessOf.instance(CostResult.instance(sacrifice));
			this.addEffect(gainLife(You.instance(), amount, "You gain life equal to the sacrificed creature's toughness."));
		}
	}

	public KheruDreadmaw(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (1)(G), Sacrifice another creature: You gain life equal to the
		// sacrificed creature's toughness.
		this.addAbility(new KheruDreadmawAbility1(state));
	}
}
