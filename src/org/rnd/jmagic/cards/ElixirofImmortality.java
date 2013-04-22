package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elixir of Immortality")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ElixirofImmortality extends Card
{
	public static final class ElixirofImmortalityAbility0 extends ActivatedAbility
	{
		public ElixirofImmortalityAbility0(GameState state)
		{
			super(state, "(2), (T): You gain 5 life. Shuffle Elixir of Immortality and your graveyard into their owner's library.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;

			this.addEffect(gainLife(You.instance(), 5, "You gain 5 life."));

			SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
			SetGenerator owner = OwnerOf.instance(ABILITY_SOURCE_OF_THIS);
			EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle Elixir of Immortality and your graveyard into your library.");
			shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
			shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(inYourGraveyard, ABILITY_SOURCE_OF_THIS, You.instance(), owner));
			this.addEffect(shuffle);
		}
	}

	public ElixirofImmortality(GameState state)
	{
		super(state);

		// (2), (T): You gain 5 life. Shuffle Elixir of Immortality and your
		// graveyard into your library.
		this.addAbility(new ElixirofImmortalityAbility0(state));
	}
}
