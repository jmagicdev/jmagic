package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Herald of War")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class HeraldofWar extends Card
{
	public static final class HeraldofWarAbility1 extends EventTriggeredAbility
	{
		public HeraldofWarAbility1(GameState state)
		{
			super(state, "Whenever Herald of War attacks, put a +1/+1 counter on it.");
			this.addPattern(whenThisAttacks());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on it."));
		}
	}

	public HeraldofWar(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Herald of War attacks, put a +1/+1 counter on it.
		this.addAbility(new HeraldofWarAbility1(state));

		// Angel spells and Human spells you cast cost (1) less to cast for each
		// +1/+1 counter on Herald of War.
		SetGenerator types = HasSubType.instance(SubType.ANGEL, SubType.HUMAN);
		SetGenerator number = Count.instance(CountersOn.instance(This.instance(), Counter.CounterType.PLUS_ONE_PLUS_ONE));
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, types, "(1)", number, "Angel spells and Human spells you cast cost (1) less to cast for each +1/+1 counter on Herald of War."));
	}
}
