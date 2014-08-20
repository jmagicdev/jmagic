package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gyre Sage")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.DRUID})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class GyreSage extends Card
{
	public static final class GyreSageAbility1 extends ActivatedAbility
	{
		public GyreSageAbility1(GameState state)
		{
			super(state, "(T): Add (G) to your mana pool for each +1/+1 counter on Gyre Sage.");
			this.costsTap = true;

			SetGenerator count = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.PLUS_ONE_PLUS_ONE));

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Add (G) to your mana pool for each +1/+1 counter on Gyre Sage.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(G)")));
			addMana.parameters.put(EventType.Parameter.MULTIPLY, count);
			addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(addMana);
		}
	}

	public GyreSage(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));

		// (T): Add (G) to your mana pool for each +1/+1 counter on Gyre Sage.
		this.addAbility(new GyreSageAbility1(state));
	}
}
