package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Increasing Vengeance")
@Types({Type.INSTANT})
@ManaCost("RR")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class IncreasingVengeance extends Card
{
	public IncreasingVengeance(GameState state)
	{
		super(state);

		// Copy target instant or sorcery spell you control. If Increasing
		// Vengeance was cast from a graveyard, copy that spell twice instead.
		// You may choose new targets for the copies.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), ControlledBy.instance(You.instance(), Stack.instance())), "target instant or sorcery spell you control"));
		SetGenerator number = IfThenElse.instance(Intersect.instance(ZoneCastFrom.instance(This.instance()), GraveyardOf.instance(Players.instance())), numberGenerator(2), numberGenerator(1));

		EventFactory factory = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy target instant or sorcery spell you control. If Increasing Vengeance was cast from a graveyard, copy that spell twice instead. You may choose new targets for the copies.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, target);
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, number);
		this.addEffect(factory);

		// Flashback (3)(R)(R) (You may cast this card from your graveyard for
		// its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(3)(R)(R)"));
	}
}
