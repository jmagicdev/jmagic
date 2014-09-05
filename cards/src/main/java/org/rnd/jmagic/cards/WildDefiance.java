package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Wild Defiance")
@Types({Type.ENCHANTMENT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class WildDefiance extends Card
{
	public static final class WildDefianceAbility0 extends EventTriggeredAbility
	{
		public WildDefianceAbility0(GameState state)
		{
			super(state, "Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn.");
			this.addPattern(new BecomesTheTargetPattern(CREATURES_YOU_CONTROL, HasType.instance(Type.INSTANT, Type.SORCERY)));

			SetGenerator thatCreature = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			this.addEffect(ptChangeUntilEndOfTurn(thatCreature, +3, +3, "That creature gets +3/+3 until end of turn."));
		}
	}

	public WildDefiance(GameState state)
	{
		super(state);

		// Whenever a creature you control becomes the target of an instant or
		// sorcery spell, that creature gets +3/+3 until end of turn.
		this.addAbility(new WildDefianceAbility0(state));
	}
}
