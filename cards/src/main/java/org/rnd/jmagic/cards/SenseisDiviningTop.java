package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sensei's Divining Top")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class SenseisDiviningTop extends Card
{
	public static final class SenseisDiviningTopAbility0 extends ActivatedAbility
	{
		public SenseisDiviningTopAbility0(GameState state)
		{
			super(state, "(1): Look at the top three cards of your library, then put them back in any order.");
			this.setManaCost(new ManaPool("(1)"));

			EventFactory effect = new EventFactory(EventType.LOOK_AND_PUT_BACK, "Look at the top three cards of your library, then put them back in any order.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(effect);
		}
	}

	public static final class SenseisDiviningTopAbility1 extends ActivatedAbility
	{
		public SenseisDiviningTopAbility1(GameState state)
		{
			super(state, "(T): Draw a card, then put Sensei's Divining Top on top of its owner's library.");
			this.costsTap = true;

			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));

			EventFactory top = new EventFactory(EventType.PUT_INTO_LIBRARY, "then put Sensei's Divining Top on top of its owner's library.");
			top.parameters.put(EventType.Parameter.CAUSE, This.instance());
			top.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			top.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
			this.addEffect(top);
		}
	}

	public SenseisDiviningTop(GameState state)
	{
		super(state);

		// (1): Look at the top three cards of your library, then put them back
		// in any order.
		this.addAbility(new SenseisDiviningTopAbility0(state));

		// (T): Draw a card, then put Sensei's Divining Top on top of its
		// owner's library.
		this.addAbility(new SenseisDiviningTopAbility1(state));
	}
}
