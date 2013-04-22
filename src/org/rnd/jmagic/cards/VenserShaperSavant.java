package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Venser, Shaper Savant")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class VenserShaperSavant extends Card
{
	public static final class ETBBounceSpellOrPermanent extends EventTriggeredAbility
	{
		public ETBBounceSpellOrPermanent(GameState state)
		{
			super(state, "When Venser, Shaper Savant enters the battlefield, return target spell or permanent to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target t = this.addTarget(Union.instance(Spells.instance(), Permanents.instance()), "target spell or permanent");
			SetGenerator targetedObject = targetedBy(t);

			EventFactory effect = new EventFactory(EventType.MOVE_OBJECTS, "Return target spell or permanent to its owner's hand.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.TO, HandOf.instance(OwnerOf.instance(targetedObject)));
			effect.parameters.put(EventType.Parameter.OBJECT, targetedObject);
			this.addEffect(effect);
		}
	}

	public VenserShaperSavant(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// When Venser, Shaper Savant enters the battlefield, return target
		// spell or permanent to its owner's hand.
		this.addAbility(new ETBBounceSpellOrPermanent(state));
	}
}
