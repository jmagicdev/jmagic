package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dimir Charm")
@Types({Type.INSTANT})
@ManaCost("UB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DimirCharm extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("DimirCharm", "Choose one to put back.", false);

	public DimirCharm(GameState state)
	{
		super(state);

		// Choose one \u2014

		// Counter target sorcery spell
		{
			SetGenerator target = targetedBy(this.addTarget(1, Intersect.instance(HasType.instance(Type.SORCERY), Spells.instance()), "target sorcery spell"));
			this.addEffect(1, counter(target, "Counter target sorcery spell"));
		}

		// destroy target creature with power 2 or less
		{
			SetGenerator target = targetedBy(this.addTarget(2, Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(null, 2))), "target creature with power 2 or less"));
			this.addEffect(2, destroy(target, "destroy target creature with power 2 or less"));
		}

		// look at the top three cards of target player's library, then put one
		// back and the rest into that player's graveyard.
		{
			SetGenerator target = targetedBy(this.addTarget(3, Players.instance(), "target player"));
			EventFactory look = look(You.instance(), TopCards.instance(3, LibraryOf.instance(target)), "look at the top three cards of target player's library,");
			this.addEffect(3, look);

			SetGenerator revealed = EffectResult.instance(look);

			EventFactory factory = new EventFactory(EventType.MOVE_CHOICE, "then put one back");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, revealed);
			factory.parameters.put(EventType.Parameter.CHOICE, Identity.instance(REASON));
			factory.parameters.put(EventType.Parameter.TO, LibraryOf.instance(target));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
			this.addEffect(3, factory);

			this.addEffect(3, putIntoGraveyard(revealed, "and the rest into that player's graveyard."));
		}
	}
}
