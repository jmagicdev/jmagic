package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hands of Binding")
@Types({Type.SORCERY})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class HandsofBinding extends Card
{
	public HandsofBinding(GameState state)
	{
		super(state);

		// Tap target creature an opponent controls. That creature doesn't untap
		// during its controller's next untap step.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls"));

		EventFactory factory = new EventFactory(EventType.TAP_HARD, "Tap target creature an opponent controls. That creature doesn't untap during its controller's next untap step.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(factory);

		// Cipher (Then you may exile this spell card encoded on a creature you
		// control. Whenever that creature deals combat damage to a player, its
		// controller may cast a copy of the encoded card without paying its
		// mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cipher(state));
	}
}
