package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sarkhan the Mad")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.SARKHAN})
@ManaCost("3BR")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class SarkhantheMad extends Card
{
	public static final class SarkhanIsBobMaher extends LoyaltyAbility
	{
		public SarkhanIsBobMaher(GameState state)
		{
			super(state, 0, "Reveal the top card of your library and put it into your hand. Sarkhan the Mad deals damage to himself equal to that card's converted mana cost.");

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			SetGenerator topCard = TopCards.instance(1, yourLibrary);

			EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal the top card of your library");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, topCard);
			this.addEffect(reveal);

			SetGenerator yourHand = HandOf.instance(You.instance());

			EventFactory put = new EventFactory(EventType.MOVE_OBJECTS, " and put it into your hand.");
			put.parameters.put(EventType.Parameter.CAUSE, This.instance());
			put.parameters.put(EventType.Parameter.TO, yourHand);
			put.parameters.put(EventType.Parameter.OBJECT, topCard);
			this.addEffect(put);

			// ... I'm a dirty cheater.
			SetGenerator thatCard = TopCards.instance(1, yourHand);
			this.addEffect(permanentDealDamage(ConvertedManaCostOf.instance(thatCard), ABILITY_SOURCE_OF_THIS, "Sarkhan the Mad deals damage to himself equal to that card's converted mana cost."));
		}
	}

	public static final class TurnThingsIntoDragons extends LoyaltyAbility
	{
		public TurnThingsIntoDragons(GameState state)
		{
			super(state, -2, "Target creature's controller sacrifices it, then that player puts a 5/5 red Dragon creature token with flying onto the battlefield.");
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			SetGenerator thatCreaturesController = ControllerOf.instance(targetedBy(target));

			EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Target creature's controller sacrifices it,");
			sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrifice.parameters.put(EventType.Parameter.PLAYER, thatCreaturesController);
			sacrifice.parameters.put(EventType.Parameter.PERMANENT, targetedBy(target));
			this.addEffect(sacrifice);

			EventFactory token = new EventFactory(EventType.CREATE_TOKEN_ON_BATTLEFIELD, "then that player puts a 5/5 red Dragon creature token with flying onto the battlefield.");
			token.parameters.put(EventType.Parameter.CAUSE, This.instance());
			token.parameters.put(EventType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			token.parameters.put(EventType.Parameter.COLOR, Identity.fromCollection(java.util.EnumSet.of(Color.RED)));
			token.parameters.put(EventType.Parameter.CONTROLLER, thatCreaturesController);
			token.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			token.parameters.put(EventType.Parameter.POWER, numberGenerator(5));
			token.parameters.put(EventType.Parameter.SUBTYPE, Identity.instance((Object)java.util.Arrays.asList(SubType.DRAGON)));
			token.parameters.put(EventType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			token.parameters.put(EventType.Parameter.TOUGHNESS, numberGenerator(5));
			this.addEffect(token);
		}
	}

	/**
	 * @eparam SOURCE: the dragons dealing damage
	 * @eparam TAKER: the target of Sarkan's -4 ability
	 * @eparam RESULT: empty
	 */
	public static final EventType DRAGONS_BITE = new EventType("DRAGONS_BITE")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set taker = parameters.get(Parameter.TAKER);
			for(GameObject dragon: parameters.get(Parameter.SOURCE).getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> oneDragonDamageParameters = new java.util.HashMap<Parameter, Set>();
				oneDragonDamageParameters.put(Parameter.SOURCE, new Set(dragon));
				oneDragonDamageParameters.put(Parameter.TAKER, taker);
				oneDragonDamageParameters.put(Parameter.NUMBER, new Set(dragon.getPower()));
				createEvent(game, dragon + " deals " + dragon.getPower() + " damage to " + taker + ".", DEAL_DAMAGE_EVENLY, oneDragonDamageParameters).perform(event, false);
			}
			event.setResult(Empty.set);
			return true;
		}

	};

	public static final class DragonsGetAngry extends LoyaltyAbility
	{
		public DragonsGetAngry(GameState state)
		{
			super(state, -4, "Each Dragon creature you control deals damage equal to its power to target player.");
			Target target = this.addTarget(Players.instance(), "target player");

			SetGenerator yourDragons = Intersect.instance(HasSubType.instance(SubType.DRAGON), CREATURES_YOU_CONTROL);

			EventFactory anger = new EventFactory(DRAGONS_BITE, "");
			anger.parameters.put(EventType.Parameter.SOURCE, yourDragons);
			anger.parameters.put(EventType.Parameter.TAKER, targetedBy(target));
			this.addEffect(anger);
		}
	}

	public SarkhantheMad(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(7);

		// 0: Reveal the top card of your library and put it into your hand.
		// Sarkhan the Mad deals damage to himself equal to that card's
		// converted mana cost.
		this.addAbility(new SarkhanIsBobMaher(state));

		// -2: Target creature's controller sacrifices it, then that player puts
		// a 5/5 red Dragon creature token with flying onto the battlefield.
		this.addAbility(new TurnThingsIntoDragons(state));

		// -4: Each Dragon creature you control deals damage equal to its power
		// to target player.
		this.addAbility(new DragonsGetAngry(state));
	}
}
