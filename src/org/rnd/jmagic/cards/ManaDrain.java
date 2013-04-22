package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mana Drain")
@Types({Type.INSTANT})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.LEGENDS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class ManaDrain extends Card
{
	public ManaDrain(GameState state)
	{
		super(state);

		// Counter target spell.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));
		this.addEffect(counter(target, "Counter target spell."));

		// At the beginning of your next main phase, add (X) to your mana pool,
		// where X is that spell's converted mana cost.
		EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add (X) to your mana pool, where X is that spell's converted mana cost.");
		mana.parameters.put(EventType.Parameter.SOURCE, This.instance());
		mana.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("1")));
		mana.parameters.put(EventType.Parameter.NUMBER, ConvertedManaCostOf.instance(delayedTriggerContext(target)));
		mana.parameters.put(EventType.Parameter.PLAYER, You.instance());

		SimpleEventPattern nextMain = new SimpleEventPattern(EventType.BEGIN_PHASE);
		nextMain.put(EventType.Parameter.PHASE, MainPhaseOf.instance(You.instance()));

		EventFactory manaLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "At the beginning of your next main phase, add (X) to your mana pool, where X is that spell's converted mana cost.");
		manaLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		manaLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(nextMain));
		manaLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(mana));
		this.addEffect(manaLater);
	}
}
