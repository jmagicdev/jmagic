package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Genesis Wave")
@Types({Type.SORCERY})
@ManaCost("XGGG")
@ColorIdentity({Color.GREEN})
public final class GenesisWave extends Card
{
	public static final PlayerInterface.ChooseReason GENESIS_WAVE_REASON = new PlayerInterface.ChooseReason("GenesisWave", "Choose any number of permanent cards with converted mana cost X or less from among the revealed cards.", true);

	public GenesisWave(GameState state)
	{
		super(state);

		// Reveal the top X cards of your library. You may put any number of
		// permanent cards with converted mana cost X or less from among them
		// onto the battlefield. Then put all cards revealed this way that
		// weren't put onto the battlefield into your graveyard.
		SetGenerator X = ValueOfX.instance(This.instance());

		EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal the top X cards of your library.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(X, LibraryOf.instance(You.instance())));
		this.addEffect(reveal);

		SetGenerator revealed = EffectResult.instance(reveal);
		SetGenerator number = Between.instance(numberGenerator(0), Count.instance(revealed));
		SetGenerator choosable = Intersect.instance(revealed, HasConvertedManaCost.instance(number), HasType.instance(Type.permanentTypes()));

		EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "Choose any number of permanent cards with converted mana cost X or less from among them.");
		choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
		choose.parameters.put(EventType.Parameter.NUMBER, number);
		choose.parameters.put(EventType.Parameter.CHOICE, choosable);
		choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, GENESIS_WAVE_REASON));
		this.addEffect(choose);

		SetGenerator putOntoBattlefield = EffectResult.instance(choose);

		EventFactory battlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put those cards onto the battlefield.");
		battlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
		battlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		battlefield.parameters.put(EventType.Parameter.OBJECT, putOntoBattlefield);
		this.addEffect(battlefield);

		EventFactory graveyard = new EventFactory(EventType.PUT_INTO_GRAVEYARD, "Then put all cards revealed this way that weren't put onto the battlefield into your graveyard.");
		graveyard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		graveyard.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(revealed, OldObjectOf.instance(EffectResult.instance(battlefield))));
		this.addEffect(graveyard);
	}
}
