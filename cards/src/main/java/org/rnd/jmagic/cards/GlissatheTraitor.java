package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glissa, the Traitor")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.ZOMBIE})
@ManaCost("BGG")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GlissatheTraitor extends Card
{
	public static final class GlissatheTraitorAbility1 extends EventTriggeredAbility
	{
		public GlissatheTraitorAbility1(GameState state)
		{
			super(state, "Whenever a creature an opponent controls dies, you may return target artifact card from your graveyard to your hand.");
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), true));

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(You.instance()))), "target artifact card in your graveyard"));

			EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target artifact card from your graveyard to your hand.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(youMay(factory, "You may return target artifact card from your graveyard to your hand."));
		}
	}

	public GlissatheTraitor(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// First strike, deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Whenever a creature an opponent controls is put into a graveyard from
		// the battlefield, you may return target artifact card from your
		// graveyard to your hand.
		this.addAbility(new GlissatheTraitorAbility1(state));
	}
}
