package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gigantiform")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Gigantiform extends Card
{
	public static final class GigantiformStatic extends StaticAbility
	{
		public GigantiformStatic(GameState state)
		{
			super(state, "Enchanted creature is 8/8 and has trample.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			SetGenerator eight = numberGenerator(8);

			this.addEffectPart(setPowerAndToughness(enchantedCreature, eight, eight));

			this.addEffectPart(addAbilityToObject(enchantedCreature, Trample.class));
		}
	}

	public static final class GigantiformTrigger extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public GigantiformTrigger(GameState state, CostCollection kickerCost)
		{
			super(state, "When Gigantiform enters the battlefield, if it was kicked, you may search your library for a card named Gigantiform, put it onto the battlefield, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			this.kickerCost = kickerCost;

			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);

			EventFactory searchFactory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a card named Gigantiform, put it onto the battlefield, then shuffle your library");
			searchFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			searchFactory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			searchFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			searchFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			searchFactory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			searchFactory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance("Gigantiform")));

			this.addEffect(youMay(searchFactory, "You may search your library for a card named Gigantiform, put it onto the battlefield, then shuffle your library."));
		}

		@Override
		public GigantiformTrigger create(Game game)
		{
			return new GigantiformTrigger(game.physicalState, this.kickerCost);
		}
	}

	public Gigantiform(GameState state)
	{
		super(state);

		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "4");
		this.addAbility(ability);

		CostCollection kickerCost = ability.costCollections[0];

		this.addAbility(new Enchant.Creature(state));

		this.addAbility(new GigantiformStatic(state));

		this.addAbility(new GigantiformTrigger(state, kickerCost));
	}
}
