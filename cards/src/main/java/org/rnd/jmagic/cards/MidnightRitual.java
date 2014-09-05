package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Midnight Ritual")
@Types({Type.SORCERY})
@ManaCost("X2B")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class MidnightRitual extends Card
{
	public MidnightRitual(GameState state)
	{
		super(state);

		SetGenerator targetRestriction = Intersect.instance(InZone.instance(GraveyardOf.instance(Players.instance())), HasType.instance(Type.CREATURE));
		Target target = this.addTarget(targetRestriction, "X target creature cards from your graveyard");
		target.setSingleNumber(ValueOfX.instance(This.instance()));
		this.addTarget(target);

		// Exile X target creature cards from your graveyard.
		EventFactory exile = exile(targetedBy(target), "Exile X target creature cards from your graveyard.");
		this.addEffect(exile);

		// For each creature card exiled this way, put a 2/2 black Zombie
		// creature token onto the battlefield.
		SetGenerator exiled = NewObjectOf.instance(EffectResult.instance(exile));
		SetGenerator creatureCardsExiled = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance(), exiled);
		SetGenerator thatMany = Count.instance(creatureCardsExiled);
		String effectName = "For each creature card exiled this way, put a 2/2 black Zombie creature token onto the battlefield.";
		CreateTokensFactory tokens = new CreateTokensFactory(thatMany, numberGenerator(2), numberGenerator(2), effectName);
		tokens.setColors(Color.BLACK);
		tokens.setSubTypes(SubType.ZOMBIE);
		this.addEffect(tokens.getEventFactory());
	}
}
