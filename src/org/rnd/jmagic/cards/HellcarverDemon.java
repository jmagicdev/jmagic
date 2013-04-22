package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hellcarver Demon")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("3BBB")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class HellcarverDemon extends Card
{
	public static final class HellcarverDemonAbility1 extends EventTriggeredAbility
	{
		public HellcarverDemonAbility1(GameState state)
		{
			super(state, "Whenever Hellcarver Demon deals combat damage to a player, sacrifice all other permanents you control and discard your hand. Exile the top six cards of your library. You may cast any number of nonland cards exiled this way without paying their mana costs.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator otherPermanents = RelativeComplement.instance(ControlledBy.instance(You.instance()), ABILITY_SOURCE_OF_THIS);

			EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Sacrifice all other permanents you control");
			sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrifice.parameters.put(EventType.Parameter.PLAYER, You.instance());
			sacrifice.parameters.put(EventType.Parameter.PERMANENT, otherPermanents);
			this.addEffect(sacrifice);

			this.addEffect(discardHand(You.instance(), "and discard your hand."));

			EventFactory exile = exile(TopCards.instance(6, LibraryOf.instance(You.instance())), "Exile the top six cards of your library.");
			this.addEffect(exile);

			SetGenerator exiledCards = NewObjectOf.instance(EffectResult.instance(exile));
			SetGenerator castable = RelativeComplement.instance(exiledCards, HasType.instance(Type.LAND));

			EventFactory castSomeCards = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast any number of nonland cards exiled this way without paying their mana costs.");
			castSomeCards.parameters.put(EventType.Parameter.CAUSE, This.instance());
			castSomeCards.parameters.put(EventType.Parameter.PLAYER, You.instance());
			castSomeCards.parameters.put(EventType.Parameter.OBJECT, castable);
			this.addEffect(castSomeCards);
		}
	}

	public HellcarverDemon(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Hellcarver Demon deals combat damage to a player, sacrifice
		// all other permanents you control and discard your hand. Exile the top
		// six cards of your library. You may cast any number of nonland cards
		// exiled this way without paying their mana costs.
		this.addAbility(new HellcarverDemonAbility1(state));
	}
}
