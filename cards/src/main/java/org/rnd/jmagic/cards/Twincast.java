package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Twincast")
@Types({Type.INSTANT})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SAVIORS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Twincast extends Card
{
	public Twincast(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(Spells.instance(), HasType.instance(Type.INSTANT, Type.SORCERY)), "target instant or sorcery spell");

		EventType.ParameterMap copyParameters = new EventType.ParameterMap();
		copyParameters.put(EventType.Parameter.CAUSE, This.instance());
		copyParameters.put(EventType.Parameter.PLAYER, You.instance());
		copyParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(new EventFactory(EventType.COPY_SPELL_OR_ABILITY, copyParameters, "Copy target instant or sorcery spell. You may choose new targets for the copy."));
	}
}
