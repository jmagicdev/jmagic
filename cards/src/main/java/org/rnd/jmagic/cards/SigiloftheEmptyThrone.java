package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sigil of the Empty Throne")
@Types({Type.ENCHANTMENT})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SigiloftheEmptyThrone extends Card
{
	public static final class SigiloftheEmptyThroneAbility0 extends EventTriggeredAbility
	{
		public SigiloftheEmptyThroneAbility0(GameState state)
		{
			super(state, "Whenever you cast an enchantment spell, put a 4/4 white Angel creature token with flying onto the battlefield.");

			SimpleEventPattern whenYouCastASpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			whenYouCastASpell.put(EventType.Parameter.PLAYER, You.instance());
			whenYouCastASpell.put(EventType.Parameter.OBJECT, HasType.instance(Type.ENCHANTMENT));
			this.addPattern(whenYouCastASpell);

			CreateTokensFactory f = new CreateTokensFactory(1, 4, 4, "Put a 4/4 white Angel creature token with flying onto the battlefield.");
			f.setColors(Color.WHITE);
			f.setSubTypes(SubType.ANGEL);
			f.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(f.getEventFactory());
		}
	}

	public SigiloftheEmptyThrone(GameState state)
	{
		super(state);

		// Whenever you cast an enchantment spell, put a 4/4 white Angel
		// creature token with flying onto the battlefield.
		this.addAbility(new SigiloftheEmptyThroneAbility0(state));
	}
}
