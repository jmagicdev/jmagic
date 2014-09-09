package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Infernal Genesis")
@Types({Type.ENCHANTMENT})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class InfernalGenesis extends Card
{
	public static final class InfernalGenesisAbility0 extends EventTriggeredAbility
	{
		public InfernalGenesisAbility0(GameState state)
		{
			super(state, "At the beginning of each player's upkeep, that player puts the top card of his or her library into his or her graveyard. Then he or she puts X 1/1 black Minion creature tokens onto the battlefield, where X is that card's converted mana cost.");

			this.addPattern(atTheBeginningOfEachPlayersUpkeep());
			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));

			EventFactory mill = millCards(thatPlayer, 1, "That player puts the top card of his or her library into his or her graveyard.");
			this.addEffect(mill);

			SetGenerator thatCard = NewObjectOf.instance(EffectResult.instance(mill));
			SetGenerator X = ConvertedManaCostOf.instance(thatCard);
			CreateTokensFactory f = new CreateTokensFactory(X, numberGenerator(1), numberGenerator(1), "Then he or she puts X 1/1 black Minion creature tokens onto the battlefield, where X is that card's converted mana cost.");
			f.setController(thatPlayer);
			f.setColors(Color.BLACK);
			f.setSubTypes(SubType.MINION);
			this.addEffect(f.getEventFactory());
		}
	}

	public InfernalGenesis(GameState state)
	{
		super(state);

		// At the beginning of each player's upkeep, that player puts the top
		// card of his or her library into his or her graveyard. Then he or she
		// puts X 1/1 black Minion creature tokens onto the battlefield, where X
		// is that card's converted mana cost.
		this.addAbility(new InfernalGenesisAbility0(state));
	}
}
