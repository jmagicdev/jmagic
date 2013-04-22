package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shimian Specter")
@Types({Type.CREATURE})
@SubTypes({SubType.SPECTER})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class ShimianSpecter extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("ShimianSpecter", "Choose a nonland card from it.", true);

	public static final class ShimianSpecterAbility1 extends EventTriggeredAbility
	{
		public ShimianSpecterAbility1(GameState state)
		{
			super(state, "Whenever Shimian Specter deals combat damage to a player, that player reveals his or her hand. You choose a nonland card from it. Search that player's graveyard, hand, and library for all cards with the same name as that card and exile them. Then that player shuffles his or her library.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator player = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));

			EventFactory reveal = reveal(HandOf.instance(player), "Target player reveals his or her hand.");
			this.addEffect(reveal);

			EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "You choose a nonland card from it. Exile that card.");
			choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
			choose.parameters.put(EventType.Parameter.CHOICE, RelativeComplement.instance(EffectResult.instance(reveal), HasType.instance(Type.LAND)));
			choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, REASON));
			this.addEffect(choose);

			SetGenerator thatCard = EffectResult.instance(choose);
			SetGenerator graveyard = GraveyardOf.instance(player);
			SetGenerator hand = HandOf.instance(player);
			SetGenerator library = LibraryOf.instance(player);
			SetGenerator zones = Union.instance(graveyard, hand, library);

			EventFactory effect = new EventFactory(EventType.SEARCH_FOR_ALL_AND_PUT_INTO, "Search that player's graveyard, hand, and library for all cards with the same name as that card and exile them.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.ZONE, zones);
			effect.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance(NameOf.instance(thatCard))));
			effect.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			this.addEffect(effect);

			this.addEffect(shuffleYourLibrary("Then shuffle your library."));
		}
	}

	public ShimianSpecter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Shimian Specter deals combat damage to a player, that player
		// reveals his or her hand. You choose a nonland card from it. Search
		// that player's graveyard, hand, and library for all cards with the
		// same name as that card and exile them. Then that player shuffles his
		// or her library.
		this.addAbility(new ShimianSpecterAbility1(state));
	}
}
