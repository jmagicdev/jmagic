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

		SetGenerator number = Between.instance(0, null);
		SetGenerator choosable = Intersect.instance(HasConvertedManaCost.instance(number), HasType.instance(Type.permanentTypes()));

		this.addEffect(Sifter.start().reveal(X).drop(number, choosable).dumpToGraveyard().getEventFactory("Reveal the top X cards of your library. You may put any number of permanent cards with converted mana cost X or less from among them onto the battlefield. Then put all cards revealed this way that weren't put onto the battlefield into your graveyard."));
	}
}
