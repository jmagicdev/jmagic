package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Astral Cornucopia")
@Types({Type.ARTIFACT})
@ManaCost("XXX")
@ColorIdentity({})
public final class AstralCornucopia extends Card
{
	public static final class AstralCornucopiaAbility1 extends ActivatedAbility
	{
		public AstralCornucopiaAbility1(GameState state)
		{
			super(state, "(T): Choose a color. Add one mana of that color to your mana pool for each charge counter on Astral Cornucopia.");
			this.costsTap = true;

			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Choose a color. Add one mana of that color to your mana pool for each charge counter on Astral Cornucopia.");
			mana.parameters.put(EventType.Parameter.SOURCE, This.instance());
			mana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(WUBRG)")));
			mana.parameters.put(EventType.Parameter.NUMBER, Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS)));
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(mana);
		}
	}

	public AstralCornucopia(GameState state)
	{
		super(state);

		// Astral Cornucopia enters the battlefield with X charge counters on
		// it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(),//
		ValueOfX.instance(This.instance()), "X charge counters on it", Counter.CounterType.CHARGE));

		// (T): Choose a color. Add one mana of that color to your mana pool for
		// each charge counter on Astral Cornucopia.
		this.addAbility(new AstralCornucopiaAbility1(state));
	}
}
