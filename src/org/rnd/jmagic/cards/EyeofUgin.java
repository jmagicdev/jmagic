package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Eye of Ugin")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class EyeofUgin extends Card
{
	public static final class SearchColorlessCreature extends ActivatedAbility
	{
		public SearchColorlessCreature(GameState state)
		{
			super(state, "(7), (T): Search your library for a colorless creature card, reveal it, and put it into your hand. Then shuffle your library.");
			this.setManaCost(new ManaPool("7"));
			this.costsTap = true;

			SetGenerator creatureCards = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance());
			SetGenerator colorlessCreatureCards = RelativeComplement.instance(creatureCards, HasColor.instance(Color.allColors()));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			parameters.put(EventType.Parameter.TYPE, colorlessCreatureCards);
			this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, parameters, "Search your library for a colorless creature card, reveal it, and put it into your hand. Then shuffle your library."));
		}
	}

	public EyeofUgin(GameState state)
	{
		super(state);

		// Colorless Eldrazi spells you cast cost (2) less to cast.
		SetGenerator eldraziSpells = Intersect.instance(HasSubType.instance(SubType.ELDRAZI), Spells.instance());
		SetGenerator colorlessEldraziSpells = RelativeComplement.instance(eldraziSpells, HasColor.instance(Color.allColors()));
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, colorlessEldraziSpells, "(2)", "Colorless Eldrazi spells you cast cost (2) less to cast."));

		// (7), (T): Search your library for a colorless creature card, reveal
		// it, and put it into your hand. Then shuffle your library.
		this.addAbility(new SearchColorlessCreature(state));
	}
}
