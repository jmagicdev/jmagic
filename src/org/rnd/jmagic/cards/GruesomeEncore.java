package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gruesome Encore")
@Types({Type.SORCERY})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class GruesomeEncore extends Card
{
	public GruesomeEncore(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(OpponentsOf.instance(You.instance())))), "target creature card in an opponent's graveyard"));

		// Put target creature card from an opponent's graveyard onto the
		// battlefield under your control. It gains haste. Exile it at the
		// beginning of the next end step. If that creature would leave the
		// battlefield, exile it instead of putting it anywhere else.
		EventFactory factory = new EventFactory(org.rnd.jmagic.abilities.keywords.Unearth.UnearthAbility.UNEARTH_EVENT, "Put target creature card from an opponent's graveyard onto the battlefield under your control. It gains haste. Exile it at the beginning of the next end step. If that creature would leave the battlefield, exile it instead of putting it anywhere else.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(factory);
	}
}
