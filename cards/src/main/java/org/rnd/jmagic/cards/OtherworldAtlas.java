package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Otherworld Atlas")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class OtherworldAtlas extends Card
{
	public static final class OtherworldAtlasAbility0 extends ActivatedAbility
	{
		public OtherworldAtlasAbility0(GameState state)
		{
			super(state, "(T): Put a charge counter on Otherworld Atlas.");
			this.costsTap = true;
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Otherworld Atlas."));
		}
	}

	public static final class OtherworldAtlasAbility1 extends ActivatedAbility
	{
		public OtherworldAtlasAbility1(GameState state)
		{
			super(state, "(T): Each player draws a card for each charge counter on Otherworld Atlas.");
			this.costsTap = true;
			SetGenerator number = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));
			this.addEffect(drawCards(Players.instance(), number, "Each player draws a card for each charge counter on Otherworld Atlas."));
		}
	}

	public OtherworldAtlas(GameState state)
	{
		super(state);

		// (T): Put a charge counter on Otherworld Atlas.
		this.addAbility(new OtherworldAtlasAbility0(state));

		// (T): Each player draws a card for each charge counter on Otherworld
		// Atlas.
		this.addAbility(new OtherworldAtlasAbility1(state));
	}
}
