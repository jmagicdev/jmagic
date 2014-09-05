package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Warren Weirding")
@Types({Type.TRIBAL, Type.SORCERY})
@SubTypes({SubType.GOBLIN})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Morningtide.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class WarrenWeirding extends Card
{
	public static final class ChoicesMadeFor extends SetGenerator
	{
		public static ChoicesMadeFor instance(SetGenerator factories)
		{
			return new ChoicesMadeFor(factories);
		}

		public static ChoicesMadeFor instance(EventFactory factory)
		{
			return new ChoicesMadeFor(Identity.instance(factory));
		}

		private SetGenerator factories;

		private ChoicesMadeFor(SetGenerator factories)
		{
			this.factories = factories;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();
			if(thisObject.isGameObject())
			{
				GameObject o = (GameObject)thisObject;
				for(EventFactory f: this.factories.evaluate(state, thisObject).getAll(EventFactory.class))
				{
					Event effectGenerated = o.getEffectGenerated(state, f);
					if(null != effectGenerated)
					{
						for(Player player: state.players)
						{
							Set choices = effectGenerated.getChoices(player);
							if(null != choices)
								ret.addAll(choices);
						}
					}
				}
			}
			return ret;
		}
	}

	public static final class WasGoblin extends SetGenerator
	{
		public static WasGoblin instance(SetGenerator what)
		{
			return new WasGoblin(what);
		}

		private SetGenerator what;

		private WasGoblin(SetGenerator what)
		{
			this.what = what;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();
			for(GameObject o: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			{
				if(o.getSubTypes().contains(SubType.GOBLIN))
					ret.add(o);
			}
			return ret;
		}

	}

	public WarrenWeirding(GameState state)
	{
		super(state);

		// Target player sacrifices a creature. If a Goblin is sacrificed this
		// way, that player puts two 1/1 black Goblin Rogue creature tokens onto
		// the battlefield, and those tokens gain haste until end of turn.

		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		EventFactory sacrifice = sacrifice(target, 1, CreaturePermanents.instance(), "Target player sacrifices a creature.");
		this.addEffect(sacrifice);

		SetGenerator sacrificed = ChoicesMadeFor.instance(sacrifice);

		CreateTokensFactory tokens = new CreateTokensFactory(2, 1, 1, "That player puts two 1/1 black Goblin Rogue creature tokens onto the battlefield.");
		tokens.setColors(Color.BLACK);
		tokens.setSubTypes(SubType.GOBLIN, SubType.ROGUE);
		EventFactory tokenFactory = tokens.getEventFactory();

		EventFactory hasteFactory = addAbilityUntilEndOfTurn(EffectResult.instance(tokenFactory), org.rnd.jmagic.abilities.keywords.Haste.class, "Those tokens gain haste until end of turn.");

		EventFactory factory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If a Goblin is sacrificed this way, that player puts two 1/1 black Goblin Rogue creature tokens onto the battlefield, and those tokens gain haste until end of turn.");
		factory.parameters.put(EventType.Parameter.IF, WasGoblin.instance(sacrificed));
		factory.parameters.put(EventType.Parameter.THEN, Identity.instance(sequence(tokenFactory, hasteFactory)));
		this.addEffect(factory);
	}
}
